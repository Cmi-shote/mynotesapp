package com.example.mynotesapp

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class NoteRepo (private val notedao: notesDao) {

    val readAllData : LiveData<List<notes>> = notedao.readAllData()

    suspend fun insertData(note: notes) {
        notedao.insert(note)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<notes>> {
        return notedao.searchDatabase(searchQuery)
    }

   suspend fun delete(note: notes){
        notedao.delete(note)
    }

    suspend fun update(note: notes){
        notedao.update(note)
    }

}
