package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mynotesapp.databinding.ActivityNewNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    private var binding : ActivityNewNoteBinding? = null
    private lateinit var mNoteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding?.backButton?.setOnClickListener {
            onBackPressed()

        }


        binding?.saveToolbar?.setOnClickListener{
            saveNote()
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


    private fun saveNote(){
        val title = binding?.inputNoteTitle?.text.toString()
        val date = binding?.DateTextView?.text.toString()
        val content = binding?.noteContent?.text.toString()

        if (inputCheck(title,content, date)){
            val note = notes(0, title, content, date)

            mNoteViewModel.insertData(note)

            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()

            Toast.makeText(applicationContext,
                "Note Added",
                Toast.LENGTH_SHORT).show()

        }else {
            Toast.makeText(applicationContext,
                "Note title or content cannot be empty",
                Toast.LENGTH_SHORT).show()
            }
        }


    private fun inputCheck(title: String, content: String, date: String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }

}
