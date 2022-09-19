package com.example.chordassistantkotlin.model

class InfoElem(
    private var Header: String,
    private var Text: String,
    private var Interval: Array<String>) {

    fun getHeader() = Header

    fun getText() = Text

    fun getInterval() = Interval

}