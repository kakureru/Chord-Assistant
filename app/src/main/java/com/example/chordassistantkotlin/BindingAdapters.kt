package com.example.chordassistantkotlin

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.example.chordassistantkotlin.constants.Instrument
import com.example.chordassistantkotlin.model.ChordsViewModel

@BindingAdapter("android:instrument")
fun bindInstrument(instrImageBtn: ImageButton, viewModel: ChordsViewModel) {
    when (viewModel.instrument.value) {
        Instrument.PIANO -> instrImageBtn.setImageResource(R.drawable.piano)
        Instrument.CELLO -> instrImageBtn.setImageResource(R.drawable.cello)
    }
}

@BindingAdapter("android:onInstrumentClick")
fun setOnInstrumentClick(instrumentImageBtn: ImageButton, viewModel: ChordsViewModel) {
    instrumentImageBtn.setOnClickListener {
        when (viewModel.instrument.value) {
            Instrument.PIANO -> instrumentImageBtn.setImageResource(R.drawable.cello)
            Instrument.CELLO -> instrumentImageBtn.setImageResource(R.drawable.piano)
        }
        viewModel.switchInstrument()
    }

}