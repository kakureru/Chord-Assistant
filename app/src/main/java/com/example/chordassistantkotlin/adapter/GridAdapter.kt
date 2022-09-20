package com.example.chordassistantkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chordassistantkotlin.R
import com.example.chordassistantkotlin.model.Chord

class GridAdapter(
    private val dataset: MutableList<Chord> = mutableListOf(),
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<GridAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        view: View,
        private val onItemClicked: (position: Int) -> Unit
    ): RecyclerView.ViewHolder(view), View.OnClickListener {
        val textView: TextView = view.findViewById(R.id.textView)
        val layout: ConstraintLayout = view.findViewById(R.id.layout)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.chord, parent, false)

        return ItemViewHolder(adapterLayout, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textView.text = dataset[position].getName()
        when (dataset[position].getStage()) {
            7 -> {
                holder.layout.setBackgroundResource(R.drawable.chord_7th)
                holder.textView.setTextColor(Color.BLACK)
            }
            9 -> {
                holder.layout.setBackgroundResource(R.drawable.chord_9th)
                holder.textView.setTextColor(Color.BLACK)
            }
            11 -> {
                holder.layout.setBackgroundResource(R.drawable.chord_11th)
                holder.textView.setTextColor(Color.BLACK)
            }
            13 -> {
                holder.layout.setBackgroundResource(R.drawable.chord_13th)
                holder.textView.setTextColor(Color.BLACK)
            }
            else -> {
                holder.layout.setBackgroundResource(R.drawable.chord_default)
                holder.textView.setTextColor(Color.WHITE)
            }
        }
    }

    override fun getItemCount(): Int = dataset.size
}