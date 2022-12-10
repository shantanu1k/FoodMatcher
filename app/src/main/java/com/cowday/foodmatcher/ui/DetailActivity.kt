package com.cowday.foodmatcher.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cowday.foodmatcher.R
import com.cowday.foodmatcher.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var beerName: String
    private lateinit var beerDescription: String
    private lateinit var beerPh: String
    private lateinit var beerTagLine: String
    private lateinit var beerImageUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.detailCustomToolbar)
        intent?.extras?.apply {
            beerName = getString("beer_name") ?: ""
            beerDescription = getString("beer_description") ?: ""
            beerPh = getString("beer_ph") ?: ""
            beerTagLine = getString("beer_tagline") ?: ""
            beerImageUrl = getString("beer_image_url") ?: ""
        }
        binding.apply {
            supportActionBar?.title = beerName
            detailCustomToolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            detailBeerName.text = beerName
            detailBeerDescription.text = beerDescription
            detailBeerPh.text = beerPh
            detailBeerTagline.text = beerTagLine
            // Picasso.get().load(beerImageUrl).placeholder(R.drawable.sample_image).into(detailBeerImage)
        }
    }
}