package com.example.mynotesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData : LiveData<List<notes>>
    private val repository : NoteRepo

    init{
        val notedao = NotesDatabase.getInstance(application).notesDao()
        repository = NoteRepo(notedao)
        readAllData = repository.readAllData
    }

    fun insertData(note: notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(note)
        }
    }

    fun updateNote(note: notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(note)
        }
    }

    fun deleteNote(note: notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(note)
        }

    }

    fun searchDatabase(searchQuery : String): LiveData<List<notes>> {
        return repository.searchDatabase(searchQuery)
    }



}