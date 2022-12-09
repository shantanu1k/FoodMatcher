package com.cowday.foodmatcher.adapter

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cowday.foodmatcher.R
import com.cowday.foodmatcher.data.BeerItem
import com.squareup.picasso.Picasso

class BeerAdapter: RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {
    private var beerList = listOf<BeerItem>()
    fun updateBeerList(newBeerList: List<BeerItem>){
        beerList = newBeerList
        notifyDataSetChanged()
    }
    class BeerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val beerImage = itemView.findViewById<ImageView>(R.id.beer_image)
        val beerName = itemView.findViewById<TextView>(R.id.beer_name)
        val beerTagLine = itemView.findViewById<TextView>(R.id.beer_tagline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return BeerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val currentItem = beerList[position]
        Picasso.get().load(currentItem.imageUrl).placeholder(R.drawable.sample_image).into(holder.beerImage)
        holder.apply {
            beerName.text = currentItem.name
            beerTagLine.text = currentItem.tagline
        }
    }

    override fun getItemCount() = beerList.size
}