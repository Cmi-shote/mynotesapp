package com.example.mynotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mynotesapp.databinding.ActivityNewNoteBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
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

        val notesdao = (application as NotesApp).db.notesDao()
        binding?.saveToolbar?.setOnClickListener{
            saveNote(notesdao)
            binding?.saveToolbar?.visibility = View.GONE
            binding?.editButton?.visibility = View.VISIBLE
            binding?.DateTextView?.visibility = View.VISIBLE
        }

        binding?.editButton?.setOnClickListener {
            binding?.saveToolbar?.visibility = View.VISIBLE
            binding?.editButton?.visibility = View.GONE
            binding?.DateTextView?.visibility = View.GONE
        }

        binding?.DateTextView?.text = SimpleDateFormat("mmmm, dd, yyyy"
            , Locale.getDefault()).format(Date())
    }

    private fun saveNote(notedao : notesDao){
        val title = binding?.inputNoteTitle?.text.toString()
        val noteText = binding?.noteContent?.text.toString()

        if (noteText.isNotEmpty() && noteText.isNotEmpty()){
            lifecycleScope.launch {
                notedao.insert(notes(0, title, noteText))
                Toast.makeText(applicationContext, "Note Saved", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(
                applicationContext, "Note Title or Content cannot be blank",
                Toast.LENGTH_SHORT).show()
        }
    }
}