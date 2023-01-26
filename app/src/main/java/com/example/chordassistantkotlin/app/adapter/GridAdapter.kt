package com.example.chordassistantkotlin.app.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.chordassistantkotlin.R
import com.example.chordassistantkotlin.domain.model.Chord


class GridAdapter(
    private val dataset: MutableList<Chord> = mutableListOf(),
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<GridAdapter.ItemViewHolder>() {

    class ItemViewHolder(
        view: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val textView: TextView = view.findViewById(R.id.textView)
        private val layout: ConstraintLayout = view.findViewById(R.id.layout)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }

        fun bind(Chord: Chord) {
            textView.text = Chord.getName()
            when (Chord.getStage()) {
                7 -> {
                    layout.setBackgroundResource(R.drawable.chord_7th)
                    textView.setTextColor(Color.BLACK)
                }
                9 -> {
                    layout.setBackgroundResource(R.drawable.chord_9th)
                    textView.setTextColor(Color.BLACK)
                }
                11 -> {
                    layout.setBackgroundResource(R.drawable.chord_11th)
                    textView.setTextColor(Color.BLACK)
                }
                13 -> {
                    layout.setBackgroundResource(R.drawable.chord_13th)
                    textView.setTextColor(Color.BLACK)
                }
                else -> {
                    layout.setBackgroundResource(R.drawable.chord_default)
                    textView.setTextColor(Color.WHITE)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.chord, parent, false)

        return ItemViewHolder(adapterLayout, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int = dataset.size
}