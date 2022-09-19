package com.example.chordassistantkotlin.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chordassistantkotlin.R
import com.example.chordassistantkotlin.model.Chord

/**
 * Adapter for the [GridView] in [MainActivity]. Displays [Chord] data object.
 */
class GridAdapter(
    private val context: Context? = null,
    private val array: MutableList<Chord> = mutableListOf()
) : BaseAdapter() {

    class ItemViewHolder(private val view: View) {

    }

    private var layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater!!.inflate(R.layout.chord, null)
        val textView = view.findViewById<TextView>(R.id.textView)
        val layout = view.findViewById<ConstraintLayout>(R.id.layout)
        textView.text = array[position].getName()
        when (array[position].getStage()) {
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
            else -> layout.setBackgroundResource(R.drawable.chord_default)
        }
        return view
    }

    override fun getCount(): Int = array.size

    override fun getItem(position: Int): Any = array[position]

    override fun getItemId(position: Int): Long = position.toLong()
}