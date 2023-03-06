package com.example.stocksapp.network

import android.media.Image
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface StocksApiService {
    @GET("quote")
    fun getProperties(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Call<DataModel>
}



