package com.example.mynotesapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class notes(title: String?, date: String?, color: String?) {

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0

    var mtitle : String? = title

    var mdate : String? = date

    var mcolor : String? = color

    fun getTitle() : String? {
        return mtitle
    }

    fun getDate() : String? {
        return mdate
    }

    fun getColor() : String? {
        return mcolor
    }
}