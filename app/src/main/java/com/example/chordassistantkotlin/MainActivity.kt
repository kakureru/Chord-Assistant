package com.example.chordassistantkotlin

import android.media.SoundPool
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.chordassistantkotlin.constants.Instrument
import com.example.chordassistantkotlin.data.SoundDatasource
import com.example.chordassistantkotlin.databinding.ActivityMainBinding
import com.example.chordassistantkotlin.model.Chord

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var soundPool: SoundPool? = SoundPool.Builder().setMaxStreams(7).build()
    private lateinit var pianoSounds: Array<Int>
    private lateinit var celloSounds: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        pianoSounds = SoundDatasource.loadPianoPool(this, soundPool)
        celloSounds = SoundDatasource.loadCelloPool(this, soundPool)

//        setupActionBarWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    fun playChord(instrument: Int, chord: Chord, tonic: Int) {
        var note = tonic
        playSound(instrument, note)
        for (interval in chord.getIntervals()) {
            note += interval
            playSound(instrument, note)
        }
    }

    fun playSound(instrument: Int, note: Int) {
        when (instrument) {
            Instrument.PIANO -> soundPool!!.play(
                pianoSounds[note],
                1f,
                1f,
                0,
                0,
                1f
            )
            Instrument.CELLO -> soundPool!!.play(
                celloSounds[note],
                1f,
                1f,
                0,
                0,
                1f
            )
        }
    }
}