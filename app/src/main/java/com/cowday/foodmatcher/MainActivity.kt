package com.cowday.foodmatcher

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowday.foodmatcher.adapter.BeerAdapter
import com.cowday.foodmatcher.databinding.ActivityMainBinding
import com.cowday.foodmatcher.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var beerAdapter: BeerAdapter
    private var isSearchOpen = false
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar)
        binding.welcomeText.visibility = View.VISIBLE
        recyclerView = binding.beerRecyclerView
        beerAdapter = BeerAdapter()
        recyclerView.apply {
            adapter = beerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        viewModel.beers.observe(this) {
            binding.progressBar.isVisible = it.isEmpty()
            beerAdapter.updateBeerList(it)
        }
        binding.searchButton.setOnClickListener {
            isSearchOpen = !isSearchOpen
            if(isSearchOpen){
                binding.apply {
                    modal.visibility = View.VISIBLE
                    searchButton.setImageResource(R.drawable.ic_round_close_24)
                    customToolbar.visibility = View.GONE
                    searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
                        android.widget.SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(foodName: String?): Boolean {
                            exitSearchButton()
                            isSearchOpen = !isSearchOpen
                            if (foodName != null) {
                                getBeersForFood(foodName)
                            }
                            return false
                        }
                        override fun onQueryTextChange(foodName: String?): Boolean {
                            return false
                        }
                    })
                }
            }
            else{
                exitSearchButton()
            }
        }
    }
    fun exitSearchButton(){
        binding.apply {
            modal.visibility = View.GONE
            searchButton.setImageResource(R.drawable.ic_round_search_24)
            customToolbar.visibility = View.VISIBLE
        }
    }
    @SuppressLint("SetTextI18n")
    fun getBeersForFood(foodName: String){
        lifecycleScope.launch {
            viewModel.getBeersForFood(normalizeText(foodName))
        }
        binding.apply {
            welcomeText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            searchResultText.visibility = View.VISIBLE
            searchResultText.clearComposingText()
            searchResultText.text = "Search results for $foodName"
        }
    }
    private fun normalizeText(foodName: String): String{
        var normalizedName = ""
        for(i in foodName){
            normalizedName += if(i == ' '){
                '_'
            } else {
                i
            }
        }
        return normalizedName
    }
}