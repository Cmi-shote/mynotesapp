package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity() : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    lateinit var noteList: List<notes>
    lateinit var notesAdapter: NotesAdapter
    var REQUEST_CODE_ADD = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notedao = (application as NotesApp).db.notesDao()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.addButton?.setOnClickListener {
            var intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)

            //REQUEST_CODE_ADD
        }

        binding?.searchIcon?.setOnClickListener {
            binding?.searchIcon?.visibility = View.GONE
            binding?.linearLayout?.visibility = View.VISIBLE
        }


        //noteList = ArrayList<notes>()
        //notesAdapter = NotesAdapter(noteList)

        //binding?.notesRecyclerView?.adapter = notesAdapter

        lifecycleScope.launch {
            notedao.getAllNotes().collect {
                val list = ArrayList(it)
                setupListOfNotesIntoRecyclerView(list, notedao)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    private fun setupListOfNotesIntoRecyclerView(
        notesList: ArrayList<notes>,
        notedao: notesDao
    ) {
        if (notesList.isNotEmpty()) {
            val itemAdapter = NotesAdapter(notesList)

            binding?.notesRecyclerView?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding?.notesRecyclerView?.adapter = itemAdapter
        }
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            lifecycleScope.launch {
                notedao.getAllNotes().collect {
                    val list = ArrayList(it)
                    setupListOfNotesIntoRecyclerView(list, notedao)

                }
            }
        }
    }*/
}