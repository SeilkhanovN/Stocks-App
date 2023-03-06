package com.example.stocksapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stocks::class], version = 2, exportSchema = false)
abstract class StocksDatabase: RoomDatabase() {
    abstract fun stocksDatabaseDao() : StocksDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: StocksDatabase? = null

        fun getInstance(context: Context) : StocksDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            StocksDatabase::class.java,
                            "stocks_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}