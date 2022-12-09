package com.cowday.foodmatcher.adapter

import android.media.Image
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return BeerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val currentItem = beerList[position]
        Picasso.get().load(currentItem.imageUrl).fit().into(holder.beerImage)
        holder.beerName.text = currentItem.name
    }

    override fun getItemCount() = beerList.size
}