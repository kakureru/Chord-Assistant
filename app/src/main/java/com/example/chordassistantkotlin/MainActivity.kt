package com.example.chordassistantkotlin

import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.chordassistantkotlin.adapter.GridAdapter
import com.example.chordassistantkotlin.data.ChordsDataSource
import com.example.chordassistantkotlin.data.SoundDatasource
import com.example.chordassistantkotlin.databinding.ActivityMainBinding
import com.example.chordassistantkotlin.model.Chord
import java.util.*

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    companion object {
        private const val KEYS_COUNT = 36
        private const val TONICS_COUNT = 12
    }

    private enum class Instrument { PIANO, CELLO }

    private var instrument = Instrument.PIANO
    private lateinit var binding: ActivityMainBinding
    private var adapter: GridAdapter? = null
    private var searchAdapter: GridAdapter? = null
    private val keys = arrayOfNulls<Button>(KEYS_COUNT) //массив клавиш
    private val tonics = arrayOfNulls<Button>(TONICS_COUNT) //массив клавиш
    private var chordsArray: MutableList<Chord> = ArrayList() //массив аккордов

    //массив аккордов, подходящих под заданные интервалы
    private val searchResultArray: ArrayList<Chord> = ArrayList()

    //массив интервалов для поиска аккордов
    private val searchIntervalsArray: ArrayList<Int> = ArrayList()

    var soundPool: SoundPool? = SoundPool.Builder().setMaxStreams(7).build()
    private var isSearchModeOn = false
    private var tonic = 0 //текущая тоника

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findKeys()
        findTonics()

        // Initialize data
        chordsArray = ChordsDataSource().loadChords(
            binding.switch7.isChecked,
            binding.switch9.isChecked,
            binding.switch11.isChecked,
            binding.switch13.isChecked
        )
        val pianoSounds = SoundDatasource().loadPianoPool(this, soundPool)
        val celloSounds = SoundDatasource().loadCelloPool(this, soundPool)

        // создание и установка адаптера таблицы аккордов
        adapter = GridAdapter(applicationContext, chordsArray)
        binding.gridView.adapter = adapter

        // переключение инструментов
        binding.imageButtonPiano.setOnClickListener { switchToPiano() }
        binding.imageButtonCello.setOnClickListener { switchToCello() }

        // вкл/выкл ступеней
        binding.switch7.setOnCheckedChangeListener(this)
        binding.switch9.setOnCheckedChangeListener(this)
        binding.switch11.setOnCheckedChangeListener(this)
        binding.switch13.setOnCheckedChangeListener(this)

        // вкл/выкл режима поиска
        binding.imageButtonFind.setOnClickListener {
            if (isSearchModeOn) offSearchMode()
            else onSearchMode()
        }

        // переход в окно справки
        binding.imageButtonInfo.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        // показ аккорда
        binding.gridView.setOnItemClickListener { _, _, position, _ ->
            clearPianoRoll()
            if (isSearchModeOn) {
                searchIntervalsArray.clear()
                showChord(searchResultArray, position, tonic, pianoSounds, celloSounds)
            } else
                showChord(chordsArray, position, tonic, pianoSounds, celloSounds)
        }

        //обработка выбора тоники
        for (i in 0 until TONICS_COUNT) {
            tonics[i]!!.setOnClickListener {
                tonics[tonic]!!.setBackgroundResource(R.drawable.rounded_button_disabled)
                tonics[i]!!.setBackgroundResource(R.drawable.rounded_button)
                tonic = i
            }
        }

        //обработка нажатий на клавиши
        for (selectedNote in 0 until KEYS_COUNT) {
            keys[selectedNote]!!.setOnClickListener {
                // play sound of chosen instrument
                if (instrument == Instrument.PIANO)
                    soundPool!!.play(pianoSounds[selectedNote], 1f, 1f, 0, 0, 1f)
                else
                    soundPool!!.play(celloSounds[selectedNote], 1f, 1f, 0, 0, 1f)

                // if search mode is on
                if (isSearchModeOn) {
                    //если только включили режим поиска
                    if (searchIntervalsArray.size == 0)
                        clearPianoRoll()

                    //проверяем не нажата ли уже выбранная ранее клавиша
                    var isAlreadySelected = false
                    for (k in searchIntervalsArray.indices)
                    //если нажата уже выбранная ранее клавиша
                        if (searchIntervalsArray[k] == selectedNote) {
                            isAlreadySelected = true
                            clearColor(selectedNote)
                            searchIntervalsArray.remove(k)

                            //проверяем остались ли выбранные ноты
                            if (searchIntervalsArray.size != 0) {
                                searchResultArray.clear()
                                findChords()
                            } else {
                                searchResultArray.clear()
                                searchAdapter!!.notifyDataSetChanged()
                            }
                            break
                        }
                    if (!isAlreadySelected) {
                        keys[selectedNote]!!.setBackgroundResource(R.drawable.key_pressed)
                        searchIntervalsArray.add(selectedNote)
                        searchIntervalsArray.sort()
                        findChords()
                    }
                }
            }
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        chordsArray.clear()
        chordsArray.addAll(
            ChordsDataSource().loadChords(
                binding.switch7.isChecked,
                binding.switch9.isChecked,
                binding.switch11.isChecked,
                binding.switch13.isChecked
            )
        )
        adapter!!.notifyDataSetChanged()
        searchResultArray.clear()
        //если сортируем во время поиска
        if (searchIntervalsArray.size != 0) {
            for (l in chordsArray.indices)
                if (chordsArray[l].getInterval().contentEquals(getIntervals(searchIntervalsArray))
                    || convertIntervals(chordsArray[l].getInterval()).contains(
                        convertIntervals(getIntervals(searchIntervalsArray))
                    )
                ) {
                    if (chordsArray[l].getStage() == 0)
                        searchResultArray.add(chordsArray[l])
                    else if (chordsArray[l].getStage() == 7 && binding.switch7.isChecked)
                        searchResultArray.add(chordsArray[l])
                    else if (chordsArray[l].getStage() == 9 && binding.switch9.isChecked)
                        searchResultArray.add(chordsArray[l])
                    else if (chordsArray[l].getStage() == 11 && binding.switch11.isChecked)
                        searchResultArray.add(chordsArray[l])
                    else if (chordsArray[l].getStage() == 13 && binding.switch13.isChecked)
                        searchResultArray.add(chordsArray[l])
                }
            searchAdapter!!.notifyDataSetChanged()
        }
    }

    private fun onSearchMode() {
        binding.imageButtonFind.alpha = 1f
        binding.lupa.alpha = 0.1f
        isSearchModeOn = true
        clearPianoRoll()
        searchAdapter = GridAdapter(applicationContext, searchResultArray)
        binding.gridView.adapter = searchAdapter
    }

    private fun offSearchMode() {
        searchIntervalsArray.clear()
        searchResultArray.clear()
        binding.imageButtonFind.alpha = 0.5f
        binding.lupa.alpha = 0f
        isSearchModeOn = false
        clearPianoRoll()
        adapter = GridAdapter(applicationContext, chordsArray)
        binding.gridView.adapter = adapter
    }

    private fun switchToPiano() {
        instrument = Instrument.PIANO
        binding.imageButtonPiano.alpha = 1f
        binding.imageButtonCello.alpha = 0.5f
    }

    private fun switchToCello() {
        instrument = Instrument.CELLO
        binding.imageButtonPiano.alpha = 0.5f
        binding.imageButtonCello.alpha = 1f
    }

    private fun showChord(
        intervalsArray: MutableList<Chord>,
        position: Int,
        startNote: Int,
        pianoSounds: Array<Int>,
        celloSounds: Array<Int>
    ) {
        var startNote = startNote
        for (i in 0..intervalsArray[position].getInterval().size) {
            keys[startNote]!!.setBackgroundResource(R.drawable.key_pressed)
            if (instrument == Instrument.PIANO)
                soundPool!!.play(pianoSounds[startNote], 1f, 1f, 0, 0, 1f)
            else
                soundPool!!.play(celloSounds[startNote], 1f, 1f, 0, 0, 1f)
            if (i != intervalsArray[position].getInterval().size)
                startNote += intervalsArray[position].getInterval()[i]
        }
    }

    //поиск подходящих аккордов
    private fun findChords() {
        searchResultArray.clear()
        for (l in 0 until chordsArray.size)
        //если интервалы полностью совпадают с интервалами аккорда или
            if (Arrays.equals(chordsArray[l].getInterval(), getIntervals(searchIntervalsArray))
                || convertIntervals(chordsArray[l].getInterval()).contains(
                    convertIntervals(
                        getIntervals(searchIntervalsArray)
                    )
                )
            )
                searchResultArray.add(chordsArray[l])
        searchAdapter!!.notifyDataSetChanged()
    }

    //преобразование интервалов из ArrayList в int[]
    private fun getIntervals(array: ArrayList<Int>): IntArray {
        val intervals = IntArray(array.size - 1)
        for (i in 0 until array.size - 1)
            intervals[i] = array[i + 1] - array[i]
        return intervals
    }

    //преобразование массива интервалов в строку интервалов разделённых пробелом
    private fun convertIntervals(arr: IntArray): String {
        val s = StringBuilder()
        for (j in arr) s.append(" ").append(j)
        return s.toString()
    }

    //возврат клавише цвета по умолчанию
    private fun clearColor(i: Int) {
        val j: Int = i % TONICS_COUNT
        if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10)
            keys[i]?.setBackgroundResource(R.drawable.black_key)
        else
            keys[i]?.setBackgroundResource(R.drawable.white_key)
    }

    //возврат цвета по умолчанию всему пианино
    private fun clearPianoRoll() {
        for (i in 0 until KEYS_COUNT) clearColor(i)
    }

    //заполнение массива тоник
    private fun findTonics() {
        tonics[0] = binding.buttonC
        tonics[1] = binding.buttonDb
        tonics[2] = binding.buttonD
        tonics[3] = binding.buttonEb
        tonics[4] = binding.buttonE
        tonics[5] = binding.buttonF
        tonics[6] = binding.buttonGb
        tonics[7] = binding.buttonG
        tonics[8] = binding.buttonAb
        tonics[9] = binding.buttonA
        tonics[10] = binding.buttonBb
        tonics[11] = binding.buttonB
    }

    //заполнение массива клавиш
    private fun findKeys() {
        keys[0] = binding.button1
        keys[1] = binding.button2
        keys[2] = binding.button3
        keys[3] = binding.button4
        keys[4] = binding.button5
        keys[5] = binding.button6
        keys[6] = binding.button7
        keys[7] = binding.button8
        keys[8] = binding.button9
        keys[9] = binding.button10
        keys[10] = binding.button11
        keys[11] = binding.button12
        keys[12] = binding.button13
        keys[13] = binding.button14
        keys[14] = binding.button15
        keys[15] = binding.button16
        keys[16] = binding.button17
        keys[17] = binding.button18
        keys[18] = binding.button19
        keys[19] = binding.button20
        keys[20] = binding.button21
        keys[21] = binding.button22
        keys[22] = binding.button23
        keys[23] = binding.button24
        keys[24] = binding.button25
        keys[25] = binding.button26
        keys[26] = binding.button27
        keys[27] = binding.button28
        keys[28] = binding.button29
        keys[29] = binding.button30
        keys[30] = binding.button31
        keys[31] = binding.button32
        keys[32] = binding.button33
        keys[33] = binding.button34
        keys[34] = binding.button35
        keys[35] = binding.button36
    }
}