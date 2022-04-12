package com.example.mynotesapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface notesDao {

    //to store data
    @Insert
    suspend fun insert(note: notes)

    @Delete
    suspend fun delete(note: notes)

    @Query("Select * FROM notesTable ORDER BY id DESC")
    fun getAllNotes():Flow<List<notes>>
}