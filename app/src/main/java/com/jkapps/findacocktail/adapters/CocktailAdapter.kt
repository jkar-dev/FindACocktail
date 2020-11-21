package com.jkapps.findacocktail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.model.Cocktail
import com.jkapps.findacocktail.utils.CircleTransformation
import com.squareup.picasso.Picasso

class CocktailAdapter(private val listener : CocktailAdapterListener): RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {
    private val cocktails : MutableList<Cocktail> = mutableListOf()

    interface CocktailAdapterListener {
        fun onItemClick(id: Int, url: String, sharedView: ImageView)
    }

    inner class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        fun bind(cocktail : Cocktail) {
            ViewCompat.setTransitionName(image, cocktail.id.toString())
            tvName.text = cocktail.name
            Picasso.get()
                .load(cocktail.image)
                .transform(CircleTransformation(10f))
                .placeholder(R.drawable.placeholder)
                .into(image)
            itemView.setOnClickListener {
                listener.onItemClick(cocktail.id,cocktail.image, image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cocktail_item, parent, false)
        return CocktailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(cocktails[position])
    }

    fun setItems(cocktails : List<Cocktail>) {
        this.cocktails.addAll(cocktails)
        notifyDataSetChanged()
    }
}