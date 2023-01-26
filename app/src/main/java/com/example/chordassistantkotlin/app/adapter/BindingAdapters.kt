package com.example.chordassistantkotlin.app

import android.util.TypedValue
import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordassistantkotlin.R
import com.example.chordassistantkotlin.app.viewmodel.ChordsViewModel
import com.example.chordassistantkotlin.domain.constants.Instrument
import kotlin.math.floor

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

@BindingAdapter("customSpanCount")
fun setSpanCount(recyclerView: RecyclerView, sColumnWidth: Float) {
    val dpInPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        sColumnWidth,
        recyclerView.context.resources.displayMetrics
    )
    val spanCount =
        floor(recyclerView.context.resources.displayMetrics.widthPixels / dpInPx).toInt()
    (recyclerView.layoutManager as GridLayoutManager).spanCount = spanCount
}