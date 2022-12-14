package com.example.chordassistantkotlin

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chordassistantkotlin.adapter.InfoAdapter
import com.example.chordassistantkotlin.data.DBHelper
import com.example.chordassistantkotlin.databinding.FragmentInfoBinding
import com.example.chordassistantkotlin.model.InfoElem

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val infoList: ArrayList<InfoElem> = ArrayList(22)
        val dbHelper = DBHelper(context)
        val database: SQLiteDatabase = dbHelper.writableDatabase
        val cursor = database.query(DBHelper.TABLE_CHORDS, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            val headerIndex = cursor.getColumnIndex(DBHelper.KEY_HEADER)
            val textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT)
            val intervalIndex = cursor.getColumnIndex(DBHelper.KEY_INTERVAL)
            do {
                val splattedIntervals = cursor.getString(intervalIndex).split(" ").toTypedArray()
                infoList.add(
                    InfoElem(
                        cursor.getString(headerIndex),
                        cursor.getString(textIndex),
                        splattedIntervals
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()

        //???????????????? ?? ?????????????????? ???????????????? ???????????? ????????????????????
        val adapter = InfoAdapter(context, infoList)
        binding.listView.adapter = adapter
    }
}