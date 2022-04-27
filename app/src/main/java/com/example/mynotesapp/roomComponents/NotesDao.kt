package com.example.mynotesapp.roomComponents

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynotesapp.data.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    //to store data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Update
    suspend fun update(note: Notes)

    @Query("SELECT * FROM notesTable ORDER BY id DESC")
    fun readAllData(): LiveData<List<Notes>>

    @Query("SELECT * FROM notesTable WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Notes>>
}