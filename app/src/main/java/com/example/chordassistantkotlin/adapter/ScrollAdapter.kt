package com.example.chordassistantkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.chordassistantkotlin.R
import com.example.chordassistantkotlin.model.InfoElem

class ScrollAdapter(
    private val context: Context? = null,
    private val array: ArrayList<InfoElem> = ArrayList(22)
) : BaseAdapter() {

    private var layoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        layoutInflater = LayoutInflater.from(context)
        val view: View
        if (array[position].getInterval()[0] == "") {
            view = layoutInflater.inflate(R.layout.elem_title, parent, false)
            view.findViewById<TextView>(R.id.textViewHeader).text = array[position].getHeader()
            view.findViewById<TextView>(R.id.textViewText).text = array[position].getText()
        } else {
            view = layoutInflater.inflate(R.layout.elem_chord, parent, false)
            val keys = arrayOfNulls<Button>(24) //массив клавиш
            keys[0] = view.findViewById(R.id.button1)
            keys[1] = view.findViewById(R.id.button2)
            keys[2] = view.findViewById(R.id.button3)
            keys[3] = view.findViewById(R.id.button4)
            keys[4] = view.findViewById(R.id.button5)
            keys[5] = view.findViewById(R.id.button6)
            keys[6] = view.findViewById(R.id.button7)
            keys[7] = view.findViewById(R.id.button8)
            keys[8] = view.findViewById(R.id.button9)
            keys[9] = view.findViewById(R.id.button10)
            keys[10] = view.findViewById(R.id.button11)
            keys[11] = view.findViewById(R.id.button12)
            keys[12] = view.findViewById(R.id.button13)
            keys[13] = view.findViewById(R.id.button14)
            keys[14] = view.findViewById(R.id.button15)
            keys[15] = view.findViewById(R.id.button16)
            keys[16] = view.findViewById(R.id.button17)
            keys[17] = view.findViewById(R.id.button18)
            keys[18] = view.findViewById(R.id.button19)
            keys[19] = view.findViewById(R.id.button20)
            keys[20] = view.findViewById(R.id.button21)
            keys[21] = view.findViewById(R.id.button22)
            keys[22] = view.findViewById(R.id.button23)
            keys[23] = view.findViewById(R.id.button24)
            view.findViewById<TextView>(R.id.textViewHeader).text = array[position].getHeader()
            view.findViewById<TextView>(R.id.textViewText).text = array[position].getText()
            var j = 0
            for (i in 0..array[position].getInterval().size) {
                keys[j]!!.setBackgroundResource(R.drawable.key_pressed)
                if (i != array[position].getInterval().size) j += array[position].getInterval()[i].toInt()
            }
        }
        return view
    }

    override fun getCount() = array.size

    override fun getItem(position: Int) = array[position]

    override fun getItemId(position: Int): Long = position.toLong()

}