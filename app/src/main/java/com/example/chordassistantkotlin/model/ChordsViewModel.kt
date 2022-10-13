package com.example.chordassistantkotlin.model

import androidx.lifecycle.ViewModel
import com.example.chordassistantkotlin.constants.Instrument
import com.example.chordassistantkotlin.data.ChordsDataSource

class ChordsViewModel : ViewModel() {

    // список аккордов
    private var _chordsList = ChordsDataSource.loadChords()
    val chordsList: MutableList<Chord> get() = _chordsList

    // список аккордов с искомыми интервалами
    private var _searchResultList = mutableListOf<Chord>()
    val searchResultList: MutableList<Chord> get() = _searchResultList

    // список нот для поиска аккордов
    private var searchList = mutableListOf<Int>()

    // список зажатых нот для поиска аккордов
    private var _pressedKeys = mutableSetOf<Int>()
    val pressedKeys: MutableSet<Int> get() = _pressedKeys

    private var _selectedChord: Chord? = null
    val selectedChord: Chord? get() = _selectedChord

    private var _tonic = 0
    val tonic: Int get() = _tonic

    private var _instrument = Instrument.PIANO
    val instrument: Int get() = _instrument

    private var _isSearchMode = false
    val isSearchMode: Boolean get() = _isSearchMode

    private var _seventh = true
    val seventh: Boolean get() = _seventh

    private var _ninth = true
    val ninth: Boolean get() = _ninth

    private var _eleventh = true
    val eleventh: Boolean get() = _eleventh

    private var _thirteenth = true
    val thirteenth: Boolean get() = _thirteenth

    fun offSearch() {
        _isSearchMode = false
        _searchResultList.clear()
        searchList.clear()
        resetPressed()
        _selectedChord = null
    }

    fun onSearch() {
        _isSearchMode = true
        resetPressed()
        _selectedChord = null
    }

    fun setTonic(tonic: Int) {
        _tonic = tonic
    }

    fun setSelectedChord(chord: Chord) {
        _selectedChord = chord
    }

    fun setInstrument(newInstrument: Int) {
        _instrument = newInstrument
    }

    fun resetPressed() {
        _pressedKeys.clear()
    }

    /**
     * Search
     */

    // поиск подходящих аккордов
    fun findChords() {
        if (searchList.size != 0) {
            _searchResultList.clear()
            for (chord in chordsList) {
                val intervalsChord = convertIntervals(chord.getIntervals())
                val intervalsThis = convertIntervals(getIntervals(searchList))
                // если интервалы полностью или частично совпадают
                if (intervalsChord.contains(intervalsThis))
                    when (chord.getStage()) {
                        0 -> _searchResultList.add(chord)
                        7 -> if (seventh) _searchResultList.add(chord)
                        9 -> if (ninth) _searchResultList.add(chord)
                        11 -> if (eleventh) _searchResultList.add(chord)
                        13 -> if (thirteenth) _searchResultList.add(chord)
                    }
            }
        }
    }

    // нажатие на клавишу
    fun onKeyPressed(selectedNote: Int) {
        //если только начинаем поиск
        if (_pressedKeys.size == 0) {
            _selectedChord = null
            searchList.clear()
        }
        // если клавиша уже выбрана
        if (pressedKeys.contains(selectedNote)) {
            pressedKeys.remove(selectedNote)
            searchList.remove(selectedNote)
        } else {
            pressedKeys.add(selectedNote)
            searchList.add(selectedNote)
            searchList.sort()
        }
    }

    // преобразование нот в интервалы
    private fun getIntervals(array: MutableList<Int>): IntArray {
        val intervals = IntArray(array.size - 1)
        for (i in 0 until array.size - 1)
            intervals[i] = array[i + 1] - array[i]
        return intervals
    }

    // преобразование массива интервалов в строку интервалов, разделённых пробелом
    private fun convertIntervals(arr: IntArray): String {
        val s = StringBuilder()
        for (j in arr) s.append(" ").append(j)
        return s.toString()
    }

    /**
     * Stages
     */

    fun onCheckedChanged(seventh: Boolean, ninth: Boolean, eleventh: Boolean, thirteenth: Boolean) {
        _seventh = seventh
        _ninth = ninth
        _eleventh = eleventh
        _thirteenth = thirteenth

        _chordsList.clear()
        ChordsDataSource.loadChords(_seventh, _ninth, _eleventh, _thirteenth)
        //если сортируем во время поиска
        if (_isSearchMode)
            findChords()
    }
}