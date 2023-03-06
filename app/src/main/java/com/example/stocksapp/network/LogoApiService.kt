package com.example.stocksapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LogoApiService {
    @GET("stock/profile2")
    fun getLogos(
        @Query("symbol") symbol: String,
        @Query("token") token: String
    ): Call<LogoUrlModel>
}

