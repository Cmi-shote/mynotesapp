package com.example.mynotesapp.roomComponents

import androidx.lifecycle.LiveData
import com.example.mynotesapp.data.Notes

class NoteRepo (private val notedao: NotesDao) {

    val readAllData : LiveData<List<Notes>> = notedao.readAllData()

    suspend fun insertData(note: Notes) {
        notedao.insert(note)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Notes>> {
        return notedao.searchDatabase(searchQuery)
    }

   suspend fun delete(note: Notes){
        notedao.delete(note)
    }

    suspend fun update(note: Notes){
        notedao.update(note)
    }

}
