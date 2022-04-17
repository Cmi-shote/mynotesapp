package com.example.mynotesapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null
    lateinit var noteList: ArrayList<notes>
    lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notedao = (application as NotesApp).db.notesDao()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.addButton?.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            startActivity(intent)

        }

        binding?.searchIcon?.setOnClickListener {
            binding?.searchIcon?.visibility = View.GONE
            binding?.linearLayout?.visibility = View.VISIBLE
        }

        binding?.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString(), notedao)
            }

        })

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
            val itemAdapter = NotesAdapter(notesList
            )
            { deleteId ->
                lifecycleScope.launch {
                    notedao.getAllNotesById(deleteId).collect { deleteNote(deleteId, notedao) }
                }

            }

            binding?.notesRecyclerView?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding?.notesRecyclerView?.adapter = itemAdapter
        }
    }


    private fun deleteNote(id:Int, nodedao:notesDao){

         val builder = AlertDialog.Builder(this)
         builder.setTitle("Delete Note")

         builder.setIcon(android.R.drawable.ic_dialog_alert)

         //if yes
         builder.setPositiveButton("Yes"){ dialogInterface, _ ->
             lifecycleScope.launch{
                 nodedao.delete(notes(id))
                 Toast.makeText(
                     applicationContext,
                     "Note deleted.",
                     Toast.LENGTH_SHORT
                 ).show()

                 dialogInterface.dismiss()
             }

         }

         //if no
         builder.setNegativeButton("No") { dialogInterface, _ ->
             dialogInterface.dismiss() // Dialog will be dismissed
         }
         // Create the AlertDialog
         val alertDialog: AlertDialog = builder.create()
         // Set other dialog properties
         alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
         alertDialog.show()  // show the dialog to UI


    }


    private fun filter(text: String, notedao: notesDao){
        val filteredNotes = ArrayList<notes>()
        lifecycleScope.launch {
            notedao.getAllNotes().collect { it ->
                val list = ArrayList(it)

                list.filterTo(filteredNotes) {
                    it.title.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                }
            }


                notesAdapter.filterList(filteredNotes)

            }

        }
}