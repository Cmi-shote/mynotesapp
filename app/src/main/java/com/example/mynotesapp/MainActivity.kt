package com.example.mynotesapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding : ActivityMainBinding? = null
    lateinit var noteList : List<notes>
    lateinit var notesAdapter : NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.addButton?.setOnClickListener{
            var intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }

        binding?.searchIcon?.setOnClickListener {
            binding?.searchIcon?.visibility = View.GONE
            binding?.linearLayout?.visibility = View.VISIBLE
        }

        binding?.notesRecyclerView?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        noteList = ArrayList<notes>()
        notesAdapter = NotesAdapter(noteList)

        binding?.notesRecyclerView?.adapter = notesAdapter
    }


    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

}