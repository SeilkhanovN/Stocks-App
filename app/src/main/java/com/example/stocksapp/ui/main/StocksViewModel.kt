package com.example.stocksapp.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.media.Image
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.core.widget.TextViewCompat.setLineHeight
import androidx.databinding.adapters.TextViewBindingAdapter
import androidx.lifecycle.*
import com.example.stocksapp.R
import com.example.stocksapp.database.Stocks
import com.example.stocksapp.database.StocksDatabaseDao
import com.example.stocksapp.network.DataModel
import com.example.stocksapp.network.LogoUrlModel
import com.example.stocksapp.network.LogosApi
import com.example.stocksapp.network.StocksApi
import com.example.stocksapp.repository.Repository
import kotlinx.coroutines.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream



class StocksViewModel (
    private val repository: Repository,
    private val application: Application) : AndroidViewModel(application) {

    var tickersList = arrayListOf<String>()
    var namesList = arrayListOf<String>()
    var logosList = arrayListOf<String>()
    var priceList : Double = 0.0
    var changeList : Double = 0.0

    private val _stocks = repository.stocks
    val stocks : LiveData<List<Stocks>>
        get() = _stocks

    fun readJson(context: Context): String?{
        val json : String
        var inputStream: InputStream? = null

        try {
            inputStream = context.assets.open("stockProfiles.json")
            json = inputStream.bufferedReader().use { it.readText() }

            val jsonarr = JSONArray(json)

            for (i in 0 until jsonarr.length()){
                val jsonobj = jsonarr.getJSONObject(i)
                tickersList.add(jsonobj.getString("ticker"))
                logosList.add(jsonobj.getString("logo"))
                namesList.add(jsonobj.getString("name"))
            }
        } catch (ex: IOException) { }
        return null
    }










    fun takeLogoUrl(i: Int) {
        LogosApi.retrofitServiceLogo.getLogos(tickersList[i], TOKEN).enqueue(object : Callback<LogoUrlModel>{
            override fun onResponse(call: Call<LogoUrlModel>, response: Response<LogoUrlModel>) {
                val body = response.body()
                if(body != null){
                    val logoUrl = body.logoUrl
                    viewModelScope.launch {
                        updateLogo(tickersList[i], logoUrl)
                    }

                }
            }
            override fun onFailure(call: Call<LogoUrlModel>, t: Throwable) {

            }
        })
    }

    private suspend fun updateLogo(ticker: String, logoUrl: String) {
        withContext(Dispatchers.IO) {
            database.updateStockLogo(ticker, logoUrl)
        }
    }

    fun setButtonsSizes(current: Button, previous: Button){
        setTextSize(current, 28.toFloat())
        setTextSize(previous, 18.toFloat())
        setLineHeight(current, 32)
        setLineHeight(previous, 24)

    }

    private fun setTextSize(current: Button, toFloat: Float) {
        current.textSize = toFloat
    }

}
