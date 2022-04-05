package com.example.mynotesapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface notesDao {

    //to store data
    @Insert
    suspend fun insert(note: notes)

    @Update
    suspend fun update(note: notes)

    @Delete
    suspend fun delete(note: notes)

    @Query("Select * FROM `notesTable`")
    fun getAllNotes(): Flow<List<notes>>

    @Query("Select * FROM `notesTable` where id=id")
    fun getEmployedById(id:Int):Flow<notes>
}