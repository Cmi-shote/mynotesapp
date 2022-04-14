package com.example.mynotesapp

import android.widget.EditText
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
class notes(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    /*@ColumnInfo(name = "content")
    var content: String = "",


     */
    )
