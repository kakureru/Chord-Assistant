package com.example.chordassistantkotlin.data

import com.example.chordassistantkotlin.MainActivity
import com.example.chordassistantkotlin.model.Chord
import java.util.ArrayList

class ChordsDataSource {
    private val chordsArray: MutableList<Chord> = ArrayList()

    //заполнение массива аккордов
    fun loadChords(
        seventh: Boolean,
        ninth: Boolean,
        eleventh: Boolean,
        thirteenth: Boolean
    ): MutableList<Chord> {
        chordsArray.clear()
        chordsArray.add(Chord("5", intArrayOf(7), 0))
        chordsArray.add(Chord("M", intArrayOf(4, 3), 0))
        chordsArray.add(Chord("m", intArrayOf(3, 4), 0))
        chordsArray.add(Chord("Dim", intArrayOf(3, 3), 0))
        chordsArray.add(Chord("Aug", intArrayOf(4, 4), 0))
        chordsArray.add(Chord("sus2", intArrayOf(2, 5), 0))
        chordsArray.add(Chord("sus4", intArrayOf(5, 2), 0))
        chordsArray.add(Chord("-5", intArrayOf(4, 2), 0))
        chordsArray.add(Chord("7/6", intArrayOf(4, 3, 2, 1), 0))
        if (seventh) {
            chordsArray.add(Chord("6", intArrayOf(4, 3, 2), 7))
            chordsArray.add(Chord("m6", intArrayOf(3, 4, 2), 7))
            chordsArray.add(Chord("7", intArrayOf(4, 3, 3), 7))
            chordsArray.add(Chord("m7", intArrayOf(3, 4, 3), 7))
            chordsArray.add(Chord("Dim7", intArrayOf(3, 3, 3), 7))
            chordsArray.add(Chord("M7", intArrayOf(4, 3, 4), 7))
            chordsArray.add(Chord("m.M7", intArrayOf(3, 4, 4), 7))
            chordsArray.add(Chord("7sus2", intArrayOf(2, 5, 3), 7))
            chordsArray.add(Chord("7sus4", intArrayOf(5, 2, 3), 7))
            chordsArray.add(Chord("7-5", intArrayOf(4, 2, 4), 7))
            chordsArray.add(Chord("7+5", intArrayOf(4, 4, 2), 7))
            chordsArray.add(Chord("m7-5", intArrayOf(3, 3, 4), 7))
            chordsArray.add(Chord("m7+5", intArrayOf(3, 5, 2), 7))
            chordsArray.add(Chord("M7sus2", intArrayOf(2, 5, 4), 7))
            chordsArray.add(Chord("M7sus4", intArrayOf(5, 2, 4), 7))
            chordsArray.add(Chord("M7-5", intArrayOf(4, 2, 5), 7))
            chordsArray.add(Chord("M7+5", intArrayOf(4, 4, 3), 7))
            chordsArray.add(Chord("m.M7-5", intArrayOf(3, 3, 5), 7))
            chordsArray.add(Chord("m.M7+5", intArrayOf(3, 5, 3), 7))
        }
        if (ninth) {
            chordsArray.add(Chord("9", intArrayOf(4, 3, 3, 4), 9))
            chordsArray.add(Chord("m9", intArrayOf(3, 4, 3, 4), 9))
            chordsArray.add(Chord("-9", intArrayOf(4, 3, 3, 3), 9))
            chordsArray.add(Chord("m-9", intArrayOf(3, 4, 3, 3), 9))
            chordsArray.add(Chord("9+", intArrayOf(4, 3, 3, 5), 9))
            chordsArray.add(Chord("9/6", intArrayOf(4, 3, 2, 5), 9))
            chordsArray.add(Chord("m9/6", intArrayOf(3, 4, 2, 5), 9))
            chordsArray.add(Chord("M9", intArrayOf(4, 3, 4, 3), 9))
            chordsArray.add(Chord("9sus4", intArrayOf(5, 2, 3, 4), 9))
            chordsArray.add(Chord("9-5", intArrayOf(4, 2, 4, 4), 9))
            chordsArray.add(Chord("9+5", intArrayOf(4, 4, 2, 4), 9))
            chordsArray.add(Chord("97", intArrayOf(4, 3, 3, 4), 9))
            chordsArray.add(Chord("m9-5", intArrayOf(3, 3, 4, 4), 9))
            chordsArray.add(Chord("m9+5", intArrayOf(3, 5, 2, 4), 9))
            chordsArray.add(Chord("m97", intArrayOf(3, 4, 3, 4), 9))
            chordsArray.add(Chord("-9sus4", intArrayOf(5, 2, 3, 3), 9))
            chordsArray.add(Chord("-9-5", intArrayOf(4, 2, 4, 3), 9))
            chordsArray.add(Chord("-9+5", intArrayOf(4, 4, 2, 3), 9))
            chordsArray.add(Chord("-97", intArrayOf(4, 3, 3, 3), 9))
            chordsArray.add(Chord("m-9-5", intArrayOf(3, 3, 4, 3), 9))
            chordsArray.add(Chord("m-9+5", intArrayOf(3, 5, 2, 3), 9))
            chordsArray.add(Chord("m-97", intArrayOf(3, 4, 3, 3), 9))
            chordsArray.add(Chord("9+sus4", intArrayOf(5, 2, 3, 5), 9))
            chordsArray.add(Chord("9+-5", intArrayOf(4, 3, 4, 5), 9))
            chordsArray.add(Chord("9++5", intArrayOf(4, 4, 2, 5), 9))
            chordsArray.add(Chord("9+7", intArrayOf(4, 3, 3, 5), 9))
        }
        if (eleventh) {
            chordsArray.add(Chord("11", intArrayOf(4, 3, 3, 4, 3), 11))
            chordsArray.add(Chord("m11", intArrayOf(3, 4, 3, 4, 3), 11))
            chordsArray.add(Chord("11+", intArrayOf(4, 3, 3, 4, 4), 11))
            chordsArray.add(Chord("m11+", intArrayOf(3, 4, 3, 4, 4), 11))
            chordsArray.add(Chord("M11", intArrayOf(4, 3, 4, 3, 3), 11))
            chordsArray.add(Chord("11-5", intArrayOf(4, 2, 4, 4, 3), 11))
            chordsArray.add(Chord("11+5", intArrayOf(4, 4, 2, 4, 3), 11))
            chordsArray.add(Chord("117", intArrayOf(4, 3, 3, 4, 3), 11))
            chordsArray.add(Chord("11-9", intArrayOf(4, 3, 3, 3, 4), 11))
            chordsArray.add(Chord("11+9", intArrayOf(4, 3, 3, 5, 2), 11))
            chordsArray.add(Chord("m11-5", intArrayOf(3, 3, 4, 4, 3), 11))
            chordsArray.add(Chord("m11+5", intArrayOf(3, 5, 2, 4, 3), 11))
            chordsArray.add(Chord("m117", intArrayOf(3, 4, 3, 4, 3), 11))
            chordsArray.add(Chord("m11-9", intArrayOf(3, 4, 3, 3, 4), 11))
            chordsArray.add(Chord("11++5", intArrayOf(4, 4, 2, 4, 4), 11))
            chordsArray.add(Chord("11+7", intArrayOf(4, 3, 3, 4, 4), 11))
            chordsArray.add(Chord("11+-9", intArrayOf(4, 3, 3, 3, 5), 11))
            chordsArray.add(Chord("11++9", intArrayOf(4, 3, 3, 5, 3), 11))
            chordsArray.add(Chord("m11++5", intArrayOf(3, 5, 2, 4, 4), 11))
            chordsArray.add(Chord("m11+7", intArrayOf(3, 4, 3, 4, 4), 11))
            chordsArray.add(Chord("m11+-9", intArrayOf(3, 4, 3, 3, 5), 11))
        }
        if (thirteenth) {
            chordsArray.add(Chord("13", intArrayOf(4, 3, 3, 4, 3, 4), 13))
            chordsArray.add(Chord("m13", intArrayOf(3, 4, 3, 4, 3, 4), 13))
            chordsArray.add(Chord("13#11", intArrayOf(4, 3, 3, 4, 4, 3), 13))
            chordsArray.add(Chord("m13#11", intArrayOf(3, 4, 3, 4, 4, 3), 13))
            chordsArray.add(Chord("M13", intArrayOf(4, 3, 4, 3, 3, 4), 13))
            chordsArray.add(Chord("13-5", intArrayOf(4, 2, 4, 4, 3, 4), 13))
            chordsArray.add(Chord("13+5", intArrayOf(4, 4, 2, 4, 3, 4), 13))
            chordsArray.add(Chord("137", intArrayOf(4, 3, 3, 4, 3, 4), 13))
            chordsArray.add(Chord("13-9", intArrayOf(4, 3, 3, 3, 4, 4), 13))
            chordsArray.add(Chord("13+9", intArrayOf(4, 3, 3, 5, 2, 4), 13))
            chordsArray.add(Chord("m13-5", intArrayOf(3, 3, 4, 4, 3, 4), 13))
            chordsArray.add(Chord("m13+5", intArrayOf(3, 5, 2, 4, 3, 4), 13))
            chordsArray.add(Chord("m137", intArrayOf(3, 4, 3, 4, 3, 4), 13))
            chordsArray.add(Chord("m13-9", intArrayOf(3, 4, 3, 3, 4, 4), 13))
            chordsArray.add(Chord("13#11+5", intArrayOf(4, 4, 2, 4, 4, 3), 13))
            chordsArray.add(Chord("13#117", intArrayOf(4, 3, 3, 4, 4, 3), 13))
            chordsArray.add(Chord("13#11-9", intArrayOf(4, 3, 3, 3, 5, 3), 13))
            chordsArray.add(Chord("13#11+9", intArrayOf(4, 3, 3, 5, 3, 3), 13))
            chordsArray.add(Chord("m13#11-5", intArrayOf(3, 3, 4, 4, 4, 3), 13))
            chordsArray.add(Chord("m13#117", intArrayOf(3, 4, 3, 4, 4, 3), 13))
            chordsArray.add(Chord("m13#11-9", intArrayOf(3, 4, 3, 3, 5, 3), 13))
        }
        return chordsArray
    }
}