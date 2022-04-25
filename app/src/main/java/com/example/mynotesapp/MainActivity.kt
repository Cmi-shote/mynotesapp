package com.example.mynotesapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var binding: ActivityMainBinding? = null
    lateinit var noteList: ArrayList<notes>
    lateinit var itemAdapter: NotesAdapter
    private lateinit var tempArrayList: ArrayList<notes>
    private lateinit var mNoteViewModel : NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val notedao = (application as NotesApp).db.notesDao()

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding?.addButton?.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)
        }

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
            itemAdapter = NotesAdapter(
                notesList
            )
           { deleteId ->

               deleteNote(deleteId)
            }


            binding?.notesRecyclerView?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding?.notesRecyclerView?.adapter = itemAdapter

            //getting all the data from room data base
            mNoteViewModel.readAllData.observe(this) {
                itemAdapter.setData(it)
            }


        }
    }


    private fun deleteNote(id: Int) {

        lifecycleScope.launch {
            val note = notes(id, "", "", "")
            //notedao.delete(notes(id, "", "", ""))
            mNoteViewModel.deleteNote(note)

            Toast.makeText(
                applicationContext,
                "Note deleted successfully.",
                Toast.LENGTH_LONG
            ).show()
        }

        /*val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){_,_->


        builder.setNegativeButton("No"){_,_ ->

        }

        builder.setTitle("Delete Note")
        builder.create().show()



         */

/*
        lifecycleScope.launch {
            nodedao.delete(notes(id))
            Toast.makeText(
                applicationContext,
                "Note deleted.",
                Toast.LENGTH_SHORT
            ).show()

        }

 */


        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true

        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchDatabase(newText)
        }
        return true
    }

    private fun searchDatabase(query: String){
        val searchQuery = "%$query%"

        mNoteViewModel.searchDatabase(searchQuery).observe(this){ list ->
            list.let {
                itemAdapter.setData(it)
            }
        }

    }

}