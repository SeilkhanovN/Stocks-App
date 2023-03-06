package com.example.stocksapp.network

import com.google.gson.annotations.SerializedName

data class DataModel(
    @SerializedName("c") val price: Double,
    @SerializedName("d") val change: Double
)

