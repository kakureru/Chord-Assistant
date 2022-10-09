package com.example.chordassistantkotlin.data

import android.content.Context
import android.media.SoundPool
import com.example.chordassistantkotlin.R


class SoundDatasource {

    //заполнение массива звуков пианино
    fun loadPianoPool(context: Context?, soundPool: SoundPool?): Array<Int> {
        return arrayOf(
            soundPool!!.load(context, R.raw.c1, 1),
            soundPool.load(context, R.raw.db1, 1),
            soundPool.load(context, R.raw.d1, 1),
            soundPool.load(context, R.raw.eb1, 1),
            soundPool.load(context, R.raw.e1, 1),
            soundPool.load(context, R.raw.f1, 1),
            soundPool.load(context, R.raw.gb1, 1),
            soundPool.load(context, R.raw.g1, 1),
            soundPool.load(context, R.raw.ab1, 1),
            soundPool.load(context, R.raw.a1, 1),
            soundPool.load(context, R.raw.bb1, 1),
            soundPool.load(context, R.raw.b1, 1),
            soundPool.load(context, R.raw.c2, 1),
            soundPool.load(context, R.raw.db2, 1),
            soundPool.load(context, R.raw.d2, 1),
            soundPool.load(context, R.raw.eb2, 1),
            soundPool.load(context, R.raw.e2, 1),
            soundPool.load(context, R.raw.f2, 1),
            soundPool.load(context, R.raw.gb2, 1),
            soundPool.load(context, R.raw.g2, 1),
            soundPool.load(context, R.raw.ab2, 1),
            soundPool.load(context, R.raw.a2, 1),
            soundPool.load(context, R.raw.bb2, 1),
            soundPool.load(context, R.raw.b2, 1),
            soundPool.load(context, R.raw.c3, 1),
            soundPool.load(context, R.raw.db3, 1),
            soundPool.load(context, R.raw.d3, 1),
            soundPool.load(context, R.raw.eb3, 1),
            soundPool.load(context, R.raw.e3, 1),
            soundPool.load(context, R.raw.f3, 1),
            soundPool.load(context, R.raw.gb3, 1),
            soundPool.load(context, R.raw.g3, 1),
            soundPool.load(context, R.raw.ab3, 1),
            soundPool.load(context, R.raw.a3, 1),
            soundPool.load(context, R.raw.bb3, 1),
            soundPool.load(context, R.raw.b3, 1)
        )
    }

    //заполнение массива звуков скрипки
    fun loadCelloPool(context: Context?, soundPool: SoundPool?): Array<Int> {
        return arrayOf(
            soundPool!!.load(context, R.raw.c1cel, 1),
            soundPool.load(context, R.raw.db1cel, 1),
            soundPool.load(context, R.raw.d1cel, 1),
            soundPool.load(context, R.raw.eb1cel, 1),
            soundPool.load(context, R.raw.e1cel, 1),
            soundPool.load(context, R.raw.f1cel, 1),
            soundPool.load(context, R.raw.gb1cel, 1),
            soundPool.load(context, R.raw.g1cel, 1),
            soundPool.load(context, R.raw.ab1cel, 1),
            soundPool.load(context, R.raw.a1cel, 1),
            soundPool.load(context, R.raw.bb1cel, 1),
            soundPool.load(context, R.raw.b1cel, 1),
            soundPool.load(context, R.raw.c2cel, 1),
            soundPool.load(context, R.raw.db2cel, 1),
            soundPool.load(context, R.raw.d2cel, 1),
            soundPool.load(context, R.raw.eb2cel, 1),
            soundPool.load(context, R.raw.e2cel, 1),
            soundPool.load(context, R.raw.f2cel, 1),
            soundPool.load(context, R.raw.gb2cel, 1),
            soundPool.load(context, R.raw.g2cel, 1),
            soundPool.load(context, R.raw.ab2cel, 1),
            soundPool.load(context, R.raw.a2cel, 1),
            soundPool.load(context, R.raw.bb2cel, 1),
            soundPool.load(context, R.raw.b2cel, 1),
            soundPool.load(context, R.raw.c3cel, 1),
            soundPool.load(context, R.raw.db3cel, 1),
            soundPool.load(context, R.raw.d3cel, 1),
            soundPool.load(context, R.raw.eb3cel, 1),
            soundPool.load(context, R.raw.e3cel, 1),
            soundPool.load(context, R.raw.f3cel, 1),
            soundPool.load(context, R.raw.gb3cel, 1),
            soundPool.load(context, R.raw.g3cel, 1),
            soundPool.load(context, R.raw.ab3cel, 1),
            soundPool.load(context, R.raw.a3cel, 1),
            soundPool.load(context, R.raw.bb3cel, 1),
            soundPool.load(context, R.raw.b3cel, 1)
        )
    }
}