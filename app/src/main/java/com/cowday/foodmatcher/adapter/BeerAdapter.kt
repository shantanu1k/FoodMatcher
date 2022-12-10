package com.cowday.foodmatcher.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cowday.foodmatcher.R
import com.cowday.foodmatcher.data.SimpleBeerItem
import com.cowday.foodmatcher.ui.DetailActivity
import com.squareup.picasso.Picasso

class BeerAdapter: RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {
    private var beerList = listOf<SimpleBeerItem>()
    fun updateBeerList(newBeerList: List<SimpleBeerItem>){
        beerList = newBeerList
        notifyDataSetChanged()
    }
    class BeerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val beerImage = itemView.findViewById<ImageView>(R.id.beer_image)
        val beerName = itemView.findViewById<TextView>(R.id.beer_name)
        val beerTagLine = itemView.findViewById<TextView>(R.id.beer_tagline)
        val beerCard = itemView.findViewById<CardView>(R.id.beer_card)
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
            beerCard.setOnClickListener {
                val i = Intent(it.context, DetailActivity::class.java)
                i.apply {
                    putExtra("beer_name",currentItem.name)
                    putExtra("beer_image_url",currentItem.imageUrl)
                    putExtra("beer_tagline",currentItem.tagline)
                    putExtra("beer_description",currentItem.description)
                    putExtra("beer_ph",currentItem.ph)
                }
                it.context.startActivity(i)
            }
        }
    }

    override fun getItemCount() = beerList.size
}