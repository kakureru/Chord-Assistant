package com.example.chordassistantkotlin

import android.media.SoundPool
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordassistantkotlin.adapter.GridAdapter
import com.example.chordassistantkotlin.adapter.TonicAdapter
import com.example.chordassistantkotlin.constants.Instrument
import com.example.chordassistantkotlin.constants.Scale
import com.example.chordassistantkotlin.data.ChordsDataSource
import com.example.chordassistantkotlin.data.SoundDatasource
import com.example.chordassistantkotlin.databinding.FragmentChordsBinding
import com.example.chordassistantkotlin.model.Chord
import java.util.*
import kotlin.math.floor


class ChordsFragment : Fragment() {

    private val keys = arrayOfNulls<Button>(Scale.KEYS_COUNT) // массив клавиш
    private var chordsList = ChordsDataSource.loadChords() // список аккордов
    private val searchList = mutableListOf<Chord>() // список аккордов с искомыми интервалами
    private val intervalsList = mutableListOf<Int>() // список интервалов для поиска аккордов
    var soundPool: SoundPool? = SoundPool.Builder().setMaxStreams(7).build()
    private lateinit var pianoSounds: Array<Int>
    private lateinit var celloSounds: Array<Int>
    private var gridAdapter = GridAdapter(chordsList) { position -> onGridItemClick(position) }
    private lateinit var searchAdapter: GridAdapter
    private var isSearchMode = false
    private var tonic = 0
    private var instrument = Instrument.PIANO
    private var _binding: FragmentChordsBinding? = null
    private val binding get() = _binding!!
    private val sColumnWidth = 78f
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChordsBinding.inflate(inflater, container, false)
        binding.apply {
            recyclerView.adapter = gridAdapter
            tonicsScrollView.adapter = TonicAdapter(Scale.TONICS) { position -> onTonicSelected(position) }
            chordsFragment = this@ChordsFragment
        }
        mRecyclerView = binding.recyclerView
        mRecyclerView.viewTreeObserver.addOnGlobalLayoutListener { setSpanCount() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pianoSounds = SoundDatasource.loadPianoPool(context, soundPool)
        celloSounds = SoundDatasource.loadCelloPool(context, soundPool)

        findKeys()

        //обработка нажатий на клавиши
        for (selectedNote in 0 until Scale.KEYS_COUNT) {
            keys[selectedNote]!!.setOnClickListener {
                // play sound of chosen instrument
                when (instrument) {
                    Instrument.PIANO -> soundPool!!.play(
                        pianoSounds[selectedNote],
                        1f,
                        1f,
                        0,
                        0,
                        1f
                    )
                    Instrument.CELLO -> soundPool!!.play(
                        celloSounds[selectedNote],
                        1f,
                        1f,
                        0,
                        0,
                        1f
                    )
                }
                // if search mode is on
                if (isSearchMode) {
                    //если только включили режим поиска
                    if (intervalsList.size == 0)
                        clearPianoRoll()

                    // нажата ли уже выбранная ранее клавиша?
                    var isAlreadySelected = false
                    for (k in intervalsList.indices)
                    // если клавиша уже выбрана
                        if (intervalsList[k] == selectedNote) {
                            isAlreadySelected = true
                            clearColor(selectedNote)
                            intervalsList.remove(k)

                            //проверяем остались ли выбранные ноты
                            if (intervalsList.size != 0) {
                                searchList.clear()
                                findChords()
                            } else {
                                searchList.clear()
                                searchAdapter.notifyDataSetChanged()
                            }
                            break
                        }
                    // если клавиша не выбрана
                    if (!isAlreadySelected) {
                        keys[selectedNote]!!.setBackgroundResource(R.drawable.key_pressed)
                        intervalsList.add(selectedNote)
                        intervalsList.sort()
                        findChords()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onGridItemClick(position: Int) {
        clearPianoRoll()
        if (isSearchMode) {
            intervalsList.clear()
            showChord(searchList, position, tonic)
        } else
            showChord(chordsList, position, tonic)
    }

    private fun onTonicSelected(position: Int) {
        binding.tonicsScrollView
            .findViewHolderForAdapterPosition(tonic)
            ?.itemView
            ?.findViewById<TextView>(R.id.textView)
            ?.setBackgroundResource(R.drawable.rounded_button_disabled)
        tonic = position
    }

    fun onCheckedChanged() {
        chordsList.clear()
        chordsList.addAll(
            ChordsDataSource.loadChords(
                binding.switch7.isChecked,
                binding.switch9.isChecked,
                binding.switch11.isChecked,
                binding.switch13.isChecked
            )
        )
        gridAdapter.notifyDataSetChanged()
        searchList.clear()
        //если сортируем во время поиска
        if (intervalsList.size != 0) {
            for (l in chordsList.indices)
                if (chordsList[l].getInterval().contentEquals(getIntervals(intervalsList))
                    || convertIntervals(chordsList[l].getInterval()).contains(
                        convertIntervals(getIntervals(intervalsList))
                    )
                ) {
                    if (chordsList[l].getStage() == 0)
                        searchList.add(chordsList[l])
                    else if (chordsList[l].getStage() == 7 && binding.switch7.isChecked)
                        searchList.add(chordsList[l])
                    else if (chordsList[l].getStage() == 9 && binding.switch9.isChecked)
                        searchList.add(chordsList[l])
                    else if (chordsList[l].getStage() == 11 && binding.switch11.isChecked)
                        searchList.add(chordsList[l])
                    else if (chordsList[l].getStage() == 13 && binding.switch13.isChecked)
                        searchList.add(chordsList[l])
                }
            searchAdapter.notifyDataSetChanged()
        }
    }

    fun switchSearchMode() {
        if (isSearchMode) offSearchMode()
        else onSearchMode()
    }

    private fun onSearchMode() {
        binding.imageButtonFind.alpha = 1f
        binding.lupa.alpha = 0.1f
        isSearchMode = true
        clearPianoRoll()
        searchAdapter = GridAdapter(searchList) { position -> onGridItemClick(position) }
        binding.recyclerView.adapter = searchAdapter
    }

    private fun offSearchMode() {
        intervalsList.clear()
        searchList.clear()
        binding.imageButtonFind.alpha = 0.5f
        binding.lupa.alpha = 0f
        isSearchMode = false
        clearPianoRoll()
        gridAdapter = GridAdapter(chordsList) { position -> onGridItemClick(position) }
        binding.recyclerView.adapter = gridAdapter
    }

    fun switchToPiano() {
        instrument = Instrument.PIANO
        binding.imageButtonPiano.alpha = 1f
        binding.imageButtonCello.alpha = 0.5f
    }

    fun switchToCello() {
        instrument = Instrument.CELLO
        binding.imageButtonPiano.alpha = 0.5f
        binding.imageButtonCello.alpha = 1f
    }

    private fun showChord(
        intervalsArray: MutableList<Chord>,
        position: Int,
        startNote: Int
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
        searchList.clear()
        for (l in 0 until chordsList.size)
        //если интервалы полностью совпадают с интервалами аккорда или
            if (Arrays.equals(chordsList[l].getInterval(), getIntervals(intervalsList))
                || convertIntervals(chordsList[l].getInterval()).contains(
                    convertIntervals(
                        getIntervals(intervalsList)
                    )
                )
            )
                searchList.add(chordsList[l])
        searchAdapter.notifyDataSetChanged()
    }

    //преобразование интервалов из ArrayList в int[]
    private fun getIntervals(array: MutableList<Int>): IntArray {
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
        val j: Int = i % Scale.TONICS.size
        if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10)
            keys[i]?.setBackgroundResource(R.drawable.black_key)
        else
            keys[i]?.setBackgroundResource(R.drawable.white_key)
    }

    //возврат цвета по умолчанию всему пианино
    private fun clearPianoRoll() {
        for (i in 0 until Scale.KEYS_COUNT) clearColor(i)
    }

    fun goToInfo() {
        findNavController().navigate(R.id.action_chordsFragment_to_infoFragment)
    }

    private fun setSpanCount() {
        val spanCount =
            floor(mRecyclerView.width / convertDPToPixels(sColumnWidth)).toInt()
        (mRecyclerView.layoutManager as GridLayoutManager).spanCount = spanCount
    }

    private fun convertDPToPixels(dp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        )
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