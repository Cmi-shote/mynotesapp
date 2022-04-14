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
import com.example.mynotesapp.databinding.UpdatenoteBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){
    private var binding: ActivityMainBinding? = null
    lateinit var noteList: ArrayList<notes>
    lateinit var notesAdapter: NotesAdapter
    var REQUEST_CODE_ADD = 1

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
            val itemAdapter = NotesAdapter(notesList,
                {
                    updateId ->
                    updateNote(updateId, notedao)
                },

                {
                    deleteId ->
                    lifecycleScope.launch{
                        notedao.getAllNotesById(deleteId).collect{
                            deleteNote(deleteId, notedao)
                        }
                    }

                }
                )

            binding?.notesRecyclerView?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding?.notesRecyclerView?.adapter = itemAdapter
        }
    }

    private fun updateNote(id:Int, notedao: notesDao){
        Toast.makeText(
            applicationContext,
            "Note Update",
            Toast.LENGTH_SHORT
        ).show()

        /*
        val intent = Intent(applicationContext, UpdatenoteBinding::class.java)
        intent.putExtra("isViewOrUpdate", true)
        val binding = UpdatenoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch{
            notedao.getAllNotesById(id).collect {

                binding.updateNoteTitle.setText(it.title)
                binding.updateContent.setText(it.content)
                binding.updateDate.text = it.date

                val title = binding.updateNoteTitle.text.toString()
                val date = binding.updateDate.text.toString()
                val content = binding.updateContent.text.toString()

                if(title.isNotEmpty() && content.isNotEmpty()) {
                    notedao.update(notes(id, title, date, content))
                    Toast.makeText(applicationContext,
                        "Note Updated",
                        Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(
                            applicationContext,
                            "Name or Email cannot be blank",
                            Toast.LENGTH_LONG
                        ).show()
                }

            }
        }

        */
    }


    private fun deleteNote(id:Int, nodedao:notesDao){
        Toast.makeText(
            applicationContext,
            "Note Deleted",
            Toast.LENGTH_SHORT
        ).show()

        /* val builder = AlertDialog.Builder(this)
         builder.setTitle("Delete Note")

         builder.setIcon(android.R.drawable.ic_dialog_alert)

         //if yes
         builder.setPositiveButton("Yes"){ dialogInterface, _ ->
             lifecycleScope.launch{
                 nodedao.delete(notes(0))
                 Toast.makeText(
                     applicationContext,
                     "Note deleted.",
                     Toast.LENGTH_LONG
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


         */
    }


}