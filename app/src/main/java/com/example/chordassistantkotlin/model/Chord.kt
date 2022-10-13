package com.example.chordassistantkotlin.model

class Chord (private var Name: String,
             private var Intervals: IntArray,
             private var Stage: Int = 0) {

    fun getName() = Name

    fun getIntervals() = Intervals

    fun getStage() = Stage

}