package com.example.stocksapp.repository

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.stocksapp.database.Stocks
import com.example.stocksapp.database.StocksDatabase
import com.example.stocksapp.network.DataModel
import com.example.stocksapp.network.LogoApiService
import com.example.stocksapp.network.StocksApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TOKEN = "ceiqn9qad3i188ivhulgceiqn9qad3i188ivhum0"
class Repository (
    private val database: StocksDatabase,
    private val stockService: StocksApiService,
    private val logoService: LogoApiService,
    private val json: JSONArray
        ) {
    val stocks = database.stocksDatabaseDao().getAllStocks()

    suspend fun getStocksDetails() {
        withContext(Dispatchers.IO){
            for (i in 0 until json.length()) {
                val jsonobj = json.getJSONObject(i)
                val ticker = jsonobj.getString("ticker")
                val name = jsonobj.getString("name")
                stockService.getProperties(ticker, TOKEN).enqueue(object : Callback<DataModel> {
                    override fun onResponse(call: Call<DataModel>, response: Response<DataModel>) {
                        val body = response.body()
                        if (body != null) {
                            val price = body.price
                            val change = body.change
                            insertStock(price, change, ticker, name)
                            Log.d("NashViewModel", "Succesfull " + i)
                        } else {
                            Log.d("NashViewModel", response.errorBody().toString())
                        }
                    }
                    override fun onFailure(call: Call<DataModel>, t: Throwable) { }
                })
            }

        }
    }

    suspend fun insertStock(price: Double, change: Double, ticker: String, name: String) {
        withContext(Dispatchers.IO) {
            val newStock = Stocks(
                null, name, ticker,
                price, change, false
            )
            database.stocksDatabaseDao().insert(newStock)
        }
    }



}