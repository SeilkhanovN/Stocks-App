package com.example.stocksapp.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stocksapp.database.StocksDatabaseDao
import com.example.stocksapp.repository.Repository

class StocksViewModelFactory(
    private val repository: Repository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StocksViewModel::class.java)) {
            return StocksViewModel(repository, application) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }
}


