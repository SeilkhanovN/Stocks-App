package com.example.stocksapp.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapp.R
import com.example.stocksapp.database.Stocks
import com.squareup.picasso.Picasso

class StocksAdapter: ListAdapter<Stocks, StocksAdapter.ViewHolder>(StockDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_stocks, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.companyName.text = item.companyName
        holder.tickerName.text = item.tickerName
        holder.price.text = item.price.toString()
        holder.changed.text = item.changed.toString()
        try{
            Picasso.get().load(item.stockLogo).into(holder.stockLogo)
            Log.d("Picasso", item.stockLogo.toString())
        }
        catch (ex: Exception){
            holder.stockLogo.setImageResource(R.drawable.ic_for_logo)
            Log.d("Picasso", "Unsuccessful")
        }

    }

//    override fun getItemCount(): Int {
//
//    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val stockLogo = item.findViewById<ImageView>(R.id.ivStockLogo)
        val companyName = item.findViewById<TextView>(R.id.tvCompanyName)
        val tickerName = item.findViewById<TextView>(R.id.tvTickerName)
        val price = item.findViewById<TextView>(R.id.tvPrice)
        val changed = item.findViewById<TextView>(R.id.tvChanged)
    }
}

class StockDiff: DiffUtil.ItemCallback<Stocks>() {
    override fun areItemsTheSame(oldItem: Stocks, newItem: Stocks): Boolean {
        return oldItem.tickerName == newItem.tickerName
    }

    override fun areContentsTheSame(oldItem: Stocks, newItem: Stocks): Boolean {
        return oldItem == newItem
    }
}