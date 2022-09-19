package com.example.chordassistantkotlin.data

import android.content.ContentValues
import android.content.Context

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "chordsDb"
        const val TABLE_CHORDS = "chords"
        const val KEY_ID = "_id"
        const val DATABASE_VERSION = 1
        const val KEY_HEADER = "header"
        const val KEY_TEXT = "description"
        const val KEY_INTERVAL = "interval"
    }

    override fun onCreate(db: SQLiteDatabase) {
        with(db) { execSQL("create table ${Companion.TABLE_CHORDS}(${Companion.KEY_ID} integer primary key,$KEY_HEADER text,$KEY_TEXT text,$KEY_INTERVAL text)") }
        val contentValues = ContentValues()
        //занесение данных
        contentValues.put(KEY_HEADER, "Основные аккорды")
        contentValues.put(
            KEY_TEXT,
            "Мажор — это один из двух ладов гармонической тональности. Характерная особенность " +
                    "мажорного звукоряда — его третья ступень, отстоящая от первой ступени на " +
                    "большую терцию.\nМинор — один из двух ладов гармонической тональности. " +
                    "Характерной особенностью минорного звукоряда является его третья ступень, " +
                    "отстоящая от первой ступени на малую терцию."
        )
        contentValues.put(KEY_INTERVAL, "")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажор (C)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из трех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E) — " +
                    "большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Минор (Cm)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из трех звуков: \nДо (C) — основной тон аккорда (тоника);\nМи бемоль " +
                    "(Eb) — малая терция (пониженная III ступень);\nСоль (G) — чистая квинта " +
                    "(V ступень)."
        )
        contentValues.put(KEY_INTERVAL, "3 4")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Аккорды с задержанием")
        contentValues.put(
            KEY_TEXT,
            "Аккордом с задержанием называется трезвучие или септаккорд, в котором третья " +
                    "ступень (III) заменена на вторую (II) или четвертую (IV) ступень. Аккорды " +
                    "для пианино и клавишных с задержанием ничем не отличаются от аналогичных " +
                    "аккордов для других инструментов.  Характерной чертой sus-аккордов является " +
                    "факт того, что из-за отсутствия терции в составе трезвучия такой аккорд " +
                    "нельзя однозначно отнести ни к мажорному, ни к минорному ладу. Однако в " +
                    "контексте музыкального произведения их принадлежность к одному из двух " +
                    "ладов может подразумеваться."
        )
        contentValues.put(KEY_INTERVAL, "")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный аккорд с задержанием (Csus2)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из трех основных звуков:\nДо (С) — основной тон аккорда (тоника);\n" +
                    "Ре (D) — большая секунда (II ступень, верхний (нисходящий) вводный тон);\n" +
                    "Соль (G) — чистая квинта (V ступень)."
        )
        contentValues.put(KEY_INTERVAL, "2 5")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный аккорд с задержанием (Csus4)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из трех основных звуков:\nДо (С) — основной тон аккорда (тоника);\n" +
                    "Фа (F) — чистая кварта (IV ступень);\nСоль (G) — чистая квинта (V ступень)."
        )
        contentValues.put(KEY_INTERVAL, "5 2")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Пауэр-аккорд (C5)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из двух звуков:\\nДо (C) — основной тон аккорда (тоника);\\nСоль " +
                    "(G) — чистая квинта (V ступень)."
        )
        contentValues.put(KEY_INTERVAL, "7")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Септаккорды")
        contentValues.put(
            KEY_TEXT,
            "Септаккордом называют аккорд, состоящий из четырех звуков, расположенных по терциям."
        )
        contentValues.put(KEY_INTERVAL, "")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Большой мажорный септаккорд (Cmaj7)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи " +
                    "(E) — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);" +
                    "\nСи (B) — большая септима (VII ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 4")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Малый минорный септаккорд (Cm7)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи " +
                    "бемоль (Eb) — малая терция (пониженная III ступень);\nСоль (G) — чистая " +
                    "квинта (V ступень);\nСи бемоль (Bb) — малая септима (пониженная VII ступень)."
        )
        contentValues.put(KEY_INTERVAL, "3 4 3")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Малый мажорный септаккорд (C7)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи " +
                    "(E) — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);" +
                    "\nСи бемоль (Bb) — малая септима (пониженная VII ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 3")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Уменьшенный септаккорд (Cdim7)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи " +
                    "бемоль (Eb) — малая терция (пониженная III ступень);\nФа диез (F#) — тритон " +
                    "(пониженная V ступень);\nСи дубль бемоль (Bbb) — большая секста (дважды " +
                    "пониженная VII ступень)."
        )
        contentValues.put(KEY_INTERVAL, "3 3 3")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Аккорды с добавленными ступенями")
        contentValues.put(
            KEY_TEXT,
            "Аккорды с добавленными ступенями — это такой вид трезвучия или септаккорда, к " +
                    "которому была добавлена дополнительная ступень."
        )
        contentValues.put(KEY_INTERVAL, "")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорное трезвучие с дополнительной IX ступенью (Cadd9)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E)" +
                    " — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);\n" +
                    "Ре (D) — большая нона (IX ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 7")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорное трезвучие с добавленной XIII ступенью (Cadd13)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E)" +
                    " — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);\n" +
                    "A (Ля) — терцдецима (XIII ступень, секста через нону)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 14")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Секстаккорды")
        contentValues.put(
            KEY_TEXT,
            "В классической музыкальной теории (то есть речь идет не только про аккорды для" +
                    " пианино и клавишных, но и для всех музыкальных инструментов) секстаккордом" +
                    " называют первое обращение трезвучия. Однако в современной музыкальной" +
                    " практике под секстаккордом подразумевают четырехзвучный аккорд, состоящий" +
                    " из трезвучия и добавленной сексты, то есть шестой (VI) ступени."
        )
        contentValues.put(KEY_INTERVAL, "")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный секстаккорд (C6)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из четырех звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E)" +
                    " — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);\nЛя" +
                    " (A) — большая секста (VI ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 2")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный секстаккорд с добавленной IX ступенью (C6/9)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из пяти звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E)" +
                    " — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);\n" +
                    "Ля (A) — большая секста (VI ступень);\nРе (D) — большая нона (IX ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 2 5")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Аккорды с надстройками")
        contentValues.put(
            KEY_TEXT,
            "Помимо более привычных аккордов, состоящих из трех и четырех звуков, существуют" +
                    " аккорды, в чей состав входит пять и более звуков. Такие аккорды для пианино" +
                    " и клавишных, а также других музыкальных инструментов образуются путем" +
                    " последовательного добавления к септаккорду дополнительных ступеней (тех" +
                    " самых надстроек), расположенных по терциям.\nК ступеням-надстройкам" +
                    " относятся XI (нона), XI (ундецима), XIII (терцдецима) и другие последующие" +
                    " ступени. Следует заметить и всегда помнить о том, что дополнительные" +
                    " ступени в составе не изменяют качество септаккорда и его гармоническую" +
                    " функцию в ладу. Говоря более простым языком, минорный септаккорд всегда " +
                    "остается минорным, вне зависимости от появившихся в его структуре надстроек." +
                    " То же самое относится и к мажорным аккордам."
        )
        contentValues.put(KEY_INTERVAL, "")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный нонаккорд (C9)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из пяти звуков:\nДо (C) — основной тон аккорда (тоника);\nМи" +
                    " (E) — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);" +
                    "\nСи бемоль (Bb) — малая септима (пониженная VII ступень);\nРе (D) —" +
                    " большая нона (IX ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 3 4")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный ундецимаккорд (C11)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из шести звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E)" +
                    " — большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);\n" +
                    "Си бемоль (Bb) — малая септима (пониженная VII ступень);\nРе (D) — большая" +
                    " нона (IX ступень);\nФа (F) — ундецима (XI ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 3 4 3")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
        contentValues.put(KEY_HEADER, "Мажорный терцдецимаккорд (C13)")
        contentValues.put(
            KEY_TEXT,
            "Аккорд состоит из семи звуков:\nДо (C) — основной тон аккорда (тоника);\nМи (E) " +
                    "— большая терция (III ступень);\nСоль (G) — чистая квинта (V ступень);\n" +
                    "Си бемоль (Bb) — малая септима (пониженная VII ступень);\nРе (D) — большая" +
                    " нона (IX ступень);\nФа (F) — ундецима (XI ступень);\nЛя (A) — терцдецима" +
                    " (XIII ступень)."
        )
        contentValues.put(KEY_INTERVAL, "4 3 3 4 3 4")
        db.insert(Companion.TABLE_CHORDS, null, contentValues)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists ${Companion.TABLE_CHORDS}")
        onCreate(db)
    }
}