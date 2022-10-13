package com.example.chordassistantkotlin

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordassistantkotlin.adapter.GridAdapter
import com.example.chordassistantkotlin.adapter.TonicAdapter
import com.example.chordassistantkotlin.constants.Instrument
import com.example.chordassistantkotlin.constants.Scale
import com.example.chordassistantkotlin.databinding.FragmentChordsBinding
import com.example.chordassistantkotlin.model.Chord
import kotlin.math.floor

class ChordsFragment : Fragment() {

    private val viewModel: ChordsViewModel by viewModels()
    private var _binding: FragmentChordsBinding? = null
    private val binding get() = _binding!!

    private val keys = arrayOfNulls<Button>(Scale.KEYS_COUNT) // массив клавиш
    private lateinit var gridAdapter: GridAdapter
    private lateinit var searchAdapter: GridAdapter
    private val sColumnWidth = 78f
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChordsBinding.inflate(inflater, container, false)
        binding.apply {
            tonicsScrollView.adapter =
                TonicAdapter(Scale.TONICS) { position -> onTonicSelected(position) }

            chordsViewModel = viewModel
            chordsFragment = this@ChordsFragment
        }
        mRecyclerView = binding.recyclerView
        mRecyclerView.viewTreeObserver.addOnGlobalLayoutListener { setSpanCount() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findKeys()
        if (viewModel.isSearchMode)
            onSearchMode()
        else
            offSearchMode()
        viewModel.selectedChord?.let { showChord(it) }
        when (viewModel.instrument) {
            Instrument.PIANO -> enablePiano()
            Instrument.CELLO -> enableCello()
        }
        //обработка нажатий на клавиши
        for (selectedNote in 0 until Scale.KEYS_COUNT) {
            keys[selectedNote]!!.setOnClickListener {
                (activity as MainActivity?)?.playSound(viewModel.instrument, selectedNote)
                if (viewModel.isSearchMode) {
                    viewModel.onKeyPressed(selectedNote)
                    refreshPianoRoll()
                    findChords()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * onClicks
     */

    private fun onGridItemClick(position: Int) {
        refreshPianoRoll()
        if (viewModel.isSearchMode) {
            viewModel.resetPressed()
            playChord(viewModel.searchResultList[position])
            showChord(viewModel.searchResultList[position])
        } else {
            playChord(viewModel.chordsList[position])
            showChord(viewModel.chordsList[position])
        }
    }

    private fun onTonicSelected(position: Int) {
        binding.tonicsScrollView
            .findViewHolderForAdapterPosition(viewModel.tonic)
            ?.itemView
            ?.findViewById<TextView>(R.id.textView)
            ?.setBackgroundResource(R.drawable.rounded_button_disabled)
        viewModel.setTonic(position)
    }

    fun onCheckedChanged() {
        viewModel.onCheckedChanged(
            binding.switch7.isChecked,
            binding.switch9.isChecked,
            binding.switch11.isChecked,
            binding.switch13.isChecked
        )
        if (viewModel.isSearchMode)
            searchAdapter.notifyDataSetChanged()
        else
            gridAdapter.notifyDataSetChanged()
    }

    /**
     * Search
     */

    //поиск подходящих аккордов
    private fun findChords() {
        viewModel.findChords()
        searchAdapter.notifyDataSetChanged()
    }

    fun switchSearchMode() {
        if (viewModel.isSearchMode) {
            viewModel.offSearch()
            offSearchMode()
        }
        else {
            viewModel.onSearch()
            onSearchMode()
        }
    }

    private fun onSearchMode() {
        binding.imageButtonFind.setImageResource(R.drawable.ic_search_enabled)
        binding.lupa.alpha = 0.1f
        refreshPianoRoll()
        searchAdapter =
            GridAdapter(viewModel.searchResultList) { position -> onGridItemClick(position) }
        binding.recyclerView.adapter = searchAdapter
    }

    private fun offSearchMode() {
        binding.imageButtonFind.setImageResource(R.drawable.ic_search_disabled)
        binding.lupa.alpha = 0f
        refreshPianoRoll()
        gridAdapter = GridAdapter(viewModel.chordsList) { position -> onGridItemClick(position) }
        binding.recyclerView.adapter = gridAdapter
    }

    /**
     * PianoRoll
     */

    // отображение аккорда на PianoRoll
    private fun showChord(chord: Chord) {
        viewModel.setSelectedChord(chord)
        var note = viewModel.tonic
        keys[note]!!.setBackgroundResource(R.drawable.key_pressed)
        for (interval in chord.getIntervals()) {
            note += interval
            keys[note]!!.setBackgroundResource(R.drawable.key_pressed)
        }
    }

    // возврат клавише цвета по умолчанию
    private fun refreshColor(i: Int) {
        val j: Int = i % Scale.TONICS.size
        if (viewModel.pressedKeys.contains(i))
            keys[i]?.setBackgroundResource(R.drawable.key_pressed)
        else if (j == 1 || j == 3 || j == 6 || j == 8 || j == 10)
            keys[i]?.setBackgroundResource(R.drawable.black_key)
        else
            keys[i]?.setBackgroundResource(R.drawable.white_key)
    }

    // возврат цвета по умолчанию всему PianoRoll
    private fun refreshPianoRoll() {
        for (i in 0 until Scale.KEYS_COUNT) refreshColor(i)
    }

    /**
     * Navigation
     */

    fun goToInfo() {
        findNavController().navigate(R.id.action_chordsFragment_to_infoFragment)
    }

    /**
     * UI
     */

    fun enablePiano() {
        viewModel.setInstrument(Instrument.PIANO)
        binding.imageButtonPiano.alpha = 1f
        binding.imageButtonCello.alpha = 0.5f
    }

    fun enableCello() {
        viewModel.setInstrument(Instrument.CELLO)
        binding.imageButtonPiano.alpha = 0.5f
        binding.imageButtonCello.alpha = 1f
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

    private fun playChord(chord: Chord) {
        (activity as MainActivity?)?.playChord(viewModel.instrument, chord, viewModel.tonic)
    }
}