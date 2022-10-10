package com.example.chordassistantkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordassistantkotlin.R

class TonicAdapter(
    private val tonicsList: List<String>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<TonicAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        view: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        val textView: TextView = view.findViewById(R.id.textView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(position: View?) {
            onItemClicked(adapterPosition)
            textView.setBackgroundResource(R.drawable.rounded_button)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.tonic, parent, false)
        return ItemViewHolder(adapterLayout, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = tonicsList[position]
        holder.textView.contentDescription = tonicsList[position]
        if (position == 0)
            holder.textView.setBackgroundResource(R.drawable.rounded_button)
    }

    override fun getItemCount() = tonicsList.size

}