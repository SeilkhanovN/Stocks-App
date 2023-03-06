package com.example.stocksapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StocksDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stocks: Stocks)

    @Query("SElECT * from stocks_table WHERE tickerName = :ticker")
    fun getStock(ticker: String) : Stocks

    @Query("SELECT * from stocks_table")
    fun getAllStocks() : LiveData<List<Stocks>>

    @Query("UPDATE stocks_table SET stockLogo = :logoUrl WHERE tickerName = :ticker")
    fun updateStockLogo(ticker: String, logoUrl: String?)

//    @Query("SELECT * FROM stocks_table")
//    fun getTonight(): SleepNight?
}