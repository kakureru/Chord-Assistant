package com.example.chordassistantkotlin.model

class Chord (private var Name: String,
             private var Interval: IntArray,
             private var Stage: Int = 0) {

    fun getName() = Name

    fun getInterval() = Interval

    fun getStage() = Stage

}