package com.example.stocksapp.database

import android.widget.ImageView
import android.widget.TextView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stocksapp.R

@Entity(tableName = "stocks_table")
data class Stocks(
    @ColumnInfo(name = "stockLogo")
    var stockLogo: String? = null,
    @ColumnInfo(name = "companyName")
    var companyName: String,
    @PrimaryKey(autoGenerate = false)
    var tickerName: String,
    @ColumnInfo(name = "price")
    var price: Double = 0.0,
    @ColumnInfo(name = "changed")
    var changed: Double = 0.0,
    @ColumnInfo(name = "isFavourite")
    var isFavourite: Boolean = false
);
