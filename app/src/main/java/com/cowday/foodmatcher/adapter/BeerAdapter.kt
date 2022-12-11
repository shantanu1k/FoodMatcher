package com.cowday.foodmatcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cowday.foodmatcher.R
import com.cowday.foodmatcher.data.SimpleBeerItem
import com.squareup.picasso.Picasso

class BeerAdapter(val listener: OnClickListener): RecyclerView.Adapter<BeerAdapter.BeerViewHolder>() {

    private var beerList = listOf<SimpleBeerItem>()

    fun updateBeerList(newBeerList: List<SimpleBeerItem>){
        beerList = newBeerList
        notifyDataSetChanged()
    }

    class BeerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val beerImage: ImageView = itemView.findViewById<ImageView>(R.id.beer_image)
        val beerName: TextView = itemView.findViewById<TextView>(R.id.beer_name)
        val beerTagLine: TextView = itemView.findViewById<TextView>(R.id.beer_tagline)
        val beerCard: CardView = itemView.findViewById<CardView>(R.id.beer_card)
    }

    interface OnClickListener{
        fun cardOnClickListener(beerItem: SimpleBeerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_beer, parent, false)
        return BeerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val currentItem = beerList[position]
        Picasso.get().load(currentItem.imageUrl).placeholder(R.drawable.placeholder_bottle).into(holder.beerImage)

        holder.apply {
            beerName.text = currentItem.name
            beerTagLine.text = currentItem.tagline
            beerCard.setOnClickListener {
                listener.cardOnClickListener(currentItem)
            }
        }
    }

    override fun getItemCount() = beerList.size
}