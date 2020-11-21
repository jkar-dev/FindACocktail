package com.jkapps.findacocktail.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.model.Entity

class ListAdapter(private val listener : (Entity) -> Unit) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private val items : MutableList<Entity> = mutableListOf()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        fun bind(item : Entity) {
            tvName.text = item.name

            itemView.setOnClickListener {
                listener.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items : List<Entity>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}