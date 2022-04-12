package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mynotesapp.databinding.ActivityNewNoteBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    private var binding : ActivityNewNoteBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }

        val notedao = (application as NotesApp).db.notesDao()

        binding?.saveToolbar?.setOnClickListener{
            saveNote(notedao)
            binding?.saveToolbar?.visibility = View.GONE
            binding?.editButton?.visibility = View.VISIBLE
            binding?.DateTextView?.visibility = View.VISIBLE
        }

        binding?.editButton?.setOnClickListener {
            binding?.saveToolbar?.visibility = View.VISIBLE
            binding?.editButton?.visibility = View.GONE
            binding?.DateTextView?.visibility = View.GONE
        }

        binding?.DateTextView?.text = SimpleDateFormat("MMMM dd, yyyy",
            Locale.getDefault()).format(Date())
    }

    private fun saveNote(notedao : notesDao){
        val title = binding?.inputNoteTitle?.text.toString()
        val date = binding?.DateTextView?.text.toString()

        if (title.isEmpty()){
            Toast.makeText(applicationContext,
                "Note Title cannot be empty",
                Toast.LENGTH_SHORT).show()
        }else {
            lifecycleScope.launch {
                notedao.insert(notes(0, title, date))
                Toast.makeText(
                    applicationContext,
                    "Note Added",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }


}