package com.example.mynotesapp.roomComponents

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotesapp.data.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData : LiveData<List<Notes>>
    private val repository : NoteRepo

    init{
        val notedao = NotesDatabase.getInstance(application).notesDao()
        repository = NoteRepo(notedao)
        readAllData = repository.readAllData
    }

    fun insertData(note: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertData(note)
        }
    }

    fun updateNote(note: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.update(note)
        }
    }

    fun deleteNote(note: Notes){
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(note)
        }

    }

    fun searchDatabase(searchQuery : String): LiveData<List<Notes>> {
        return repository.searchDatabase(searchQuery)
    }



}