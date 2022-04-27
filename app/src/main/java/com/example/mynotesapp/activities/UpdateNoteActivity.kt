package com.example.mynotesapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mynotesapp.data.Notes
import com.example.mynotesapp.roomComponents.NoteViewModel
import com.example.mynotesapp.databinding.ActivityUpdatenoteBinding
import java.text.SimpleDateFormat
import java.util.*

class UpdateNoteActivity : AppCompatActivity() {
    private var binding: ActivityUpdatenoteBinding? = null
    private lateinit var mNoteViewModel: NoteViewModel

    var id : Int = 0

    var color : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdatenoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }

        val data = intent

        binding?.updateNoteTitle?.setText(data.getStringExtra("title"))
        binding?.updateDate?.text = data.getStringExtra("date")
        binding?.updateContent?.setText(data.getStringExtra("content"))
        id = data.getIntExtra("id", id)
        color = data.getIntExtra("color", color)

        binding?.updateContent?.setOnFocusChangeListener { view, b ->
            if (b)
                visibility()
        }

        binding?.updateNoteTitle?.setOnFocusChangeListener { view, b ->
            if (b)
                visibility()
        }

        binding?.editButton?.setOnClickListener {
            visibility()
        }

        binding?.saveToolbar?.setOnClickListener {
            updateNote(id)
        }

}

    private fun updateNote(id: Int){
        val title = binding?.updateNoteTitle?.text.toString()
        val content = binding?.updateContent?.text.toString()
        val date = SimpleDateFormat("MMMM dd, yyyy",
            Locale.getDefault()).format(Date())

        if(inputCheck(title, content, date)){
            //create note object
            val updatedNote = Notes(id, title, content, date, color)
            //update current user
            mNoteViewModel.updateNote(updatedNote)

            Toast.makeText(this, "Note Updated!", Toast.LENGTH_LONG).show()

            //go back to main activity
            finish()
        }else{
            Toast.makeText(this,
                "Note title or content cannot be empty",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, content: String, date: String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }

    private fun visibility(){
        binding?.updateDate?.text = SimpleDateFormat("MMMM dd, yyyy",
            Locale.getDefault()).format(Date())
        binding?.saveToolbar?.visibility = View.VISIBLE
        binding?.editButton?.visibility = View.GONE
    }
}
