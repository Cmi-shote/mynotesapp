package com.example.mynotesapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface notesDao {

    //to store data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: notes)

    @Delete
    suspend fun delete(note: notes)

    @Update
    suspend fun update(note: notes)

    @Query("Select * FROM notesTable ORDER BY id DESC")
    fun getAllNotes():Flow<List<notes>>

    @Query("Select * from `notesTable` where id=:id")
    fun getAllNotesById(id:Int):Flow<notes>
}