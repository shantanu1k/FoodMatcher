package com.cowday.foodmatcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowday.foodmatcher.adapter.BeerAdapter
import com.cowday.foodmatcher.data.Beer
import com.cowday.foodmatcher.data.BeerItem
import com.cowday.foodmatcher.databinding.ActivityMainBinding
import com.cowday.foodmatcher.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var beerList: List<BeerItem>
    private lateinit var beerAdapter: BeerAdapter
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar)
        lifecycleScope.launchWhenCreated {
            viewModel.getDataFromNetwork()
        }
        recyclerView = binding.beerRecyclerView
        beerAdapter = BeerAdapter()
        recyclerView.apply {
            adapter = beerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        viewModel.beers.observe(this, Observer {
            beerAdapter.updateBeerList(it)
        })
    }
}