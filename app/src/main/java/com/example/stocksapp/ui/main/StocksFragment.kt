package com.example.stocksapp.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.core.widget.TextViewCompat.setLineHeight
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setTextSize
import androidx.lifecycle.Observer
import com.example.stocksapp.MyApp
import com.example.stocksapp.R
import com.example.stocksapp.database.Stocks
import com.example.stocksapp.database.StocksDatabase
import com.example.stocksapp.databinding.FragmentStocksBinding
import com.example.stocksapp.repository.Repository


class StocksFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStocksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_stocks, container, false)

        val application = requireNotNull(this.activity).application
        val repository = (application as MyApp).repository
        val viewModelFactory = StocksViewModelFactory(repository, application)
        val viewModel =
            ViewModelProvider(
                this, viewModelFactory)[StocksViewModel::class.java]

        binding.lifecycleOwner = this

        val adapter = StocksAdapter()
        binding.stocks.adapter = adapter


        viewModel.stocks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.textBtnStocks.setOnClickListener {
            val current = binding.textBtnStocks
            val previous = binding.textBtnFavourite
            viewModel.setButtonsSizes(current, previous)
            current.setTextColor(getColor(requireContext(),R.color.selected_tv))
            previous.setTextColor(getColor(requireContext(),R.color.unselected_tv))

        }

        binding.textBtnFavourite.setOnClickListener {
            val current = binding.textBtnFavourite
            val previous = binding.textBtnStocks
            viewModel.setButtonsSizes(current, previous)
            current.setTextColor(getColor(requireContext(),R.color.selected_tv))
            previous.setTextColor(getColor(requireContext(),R.color.unselected_tv))

        }


        viewModel.readJson(this.requireContext())

        viewModel.getStocksDetails()



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel

    }

}


