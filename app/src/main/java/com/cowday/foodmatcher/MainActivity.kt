package com.cowday.foodmatcher

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cowday.foodmatcher.adapter.BeerAdapter
import com.cowday.foodmatcher.data.SimpleBeerItem
import com.cowday.foodmatcher.databinding.ActivityMainBinding
import com.cowday.foodmatcher.model.MainViewModel
import com.cowday.foodmatcher.ui.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BeerAdapter.OnClickListener {
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
        beerAdapter = BeerAdapter(this)
        recyclerView.apply {
            adapter = beerAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        viewModel.beers.observe(this) {
            binding.progressBar.isVisible = it.isEmpty()
            beerAdapter.updateBeerList(it)
            if(it.isEmpty()){
                Toast.makeText(this, getString(R.string.no_beer_found), Toast.LENGTH_SHORT).show()
                binding.progressBar.isVisible = false
            }
        }
        binding.searchButton.setOnClickListener {
            isSearchOpen = !isSearchOpen
            if(isSearchOpen){
                binding.beerRecyclerView.visibility = View.INVISIBLE
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
        binding.beerRecyclerView.visibility = View.VISIBLE
        binding.apply {
            modal.visibility = View.GONE
            searchButton.setImageResource(R.drawable.ic_round_search_24)
            customToolbar.visibility = View.VISIBLE
        }
    }
    @SuppressLint("SetTextI18n")
    fun getBeersForFood(foodName: String){
        lifecycleScope.launch {
            if(isNetworkAvailable()){
                viewModel.getBeersForFood(viewModel.normalizeText(foodName))
            }
            else{
                Toast.makeText(this@MainActivity, getString(R.string.device_offline_text), Toast.LENGTH_SHORT).show()
            }
            viewModel.getBeersForFoodFromDatabase(viewModel.normalizeText(foodName))
        }
        binding.apply {
            welcomeText.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            searchResultText.visibility = View.VISIBLE
            searchResultText.clearComposingText()
            searchResultText.text = "Search results for $foodName"
            viewModel.apply {
                searchResultTextVisibility = true
                searchResultText = foodName
                welcomeTextVisibility = false
            }
        }
    }
    override fun onStart() {
        super.onStart()
        //For configuration changes
        if(viewModel.searchResultTextVisibility){
            binding.apply {
                searchResultText.visibility = View.VISIBLE
                searchResultText.clearComposingText()
                searchResultText.text = "Search results for ${viewModel.searchResultText}"
                welcomeText.visibility = View.GONE
            }
        }
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun cardOnClickListener(beerItem: SimpleBeerItem) {
        val i = Intent(this, DetailActivity::class.java)
        i.apply {
            putExtra("beer_name",beerItem.name)
            putExtra("beer_image_url",beerItem.imageUrl)
            putExtra("beer_tagline",beerItem.tagline)
            putExtra("beer_description",beerItem.description)
            putExtra("beer_ph",beerItem.ph)
        }
        startActivity(i)
    }

}