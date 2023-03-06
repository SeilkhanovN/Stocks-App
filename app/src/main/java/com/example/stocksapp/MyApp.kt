package com.example.stocksapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.stocksapp.database.StocksDatabase
import com.example.stocksapp.network.LogoApiService
import com.example.stocksapp.network.StocksApiService
import com.example.stocksapp.repository.Repository
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException
import java.io.InputStream

private const val BASE_URL = "https://finnhub.io/api/v1/"

class MyApp : Application() {
    lateinit var database: StocksDatabase
    lateinit var stockService: StocksApiService
    lateinit var logoService: LogoApiService
    lateinit var repository: Repository
    lateinit var jsonarr: JSONArray


    override fun onCreate() {
        super.onCreate()
        database = StocksDatabase.getInstance(this)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        stockService = retrofit.create(StocksApiService::class.java)
        logoService = retrofit.create(LogoApiService::class.java)

        readJson(this)

        repository = Repository(
            database,
            stockService,
            logoService,
            jsonarr
        )
    }

    fun readJson(context: Context): String?{
        val json : String
        var inputStream: InputStream? = null
        try {
            inputStream = context.assets.open("stockProfiles.json")
            json = inputStream.bufferedReader().use { it.readText() }
            jsonarr = JSONArray(json)
        } catch (_: IOException) {
            Log.d("Json Error", "Error")
        }
        return null
    }
}
