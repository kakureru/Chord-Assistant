package com.example.chordassistantkotlin

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.chordassistantkotlin.adapter.ScrollAdapter
import com.example.chordassistantkotlin.data.DBHelper
import com.example.chordassistantkotlin.model.InfoElem

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val infoList: ArrayList<InfoElem> = ArrayList(22)
        val dbHelper = DBHelper(this)
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

        //создание и установка адаптера списка информации
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ScrollAdapter(applicationContext, infoList)
        listView.adapter = adapter

        //обработка нажатия кнопки назад
        val backButton = findViewById<ImageButton>(R.id.imageButtonBack)
        backButton.setOnClickListener { onBackPressed() }
    }
}