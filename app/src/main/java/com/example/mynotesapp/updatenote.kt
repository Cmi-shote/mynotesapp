package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Update
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.example.mynotesapp.databinding.ActivityUpdatenoteBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class updatenote : AppCompatActivity() {
    private var binding: ActivityUpdatenoteBinding? = null
    private lateinit var mNoteViewModel: NoteViewModel

    var id : Int = 0

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

        binding?.updateContent?.setOnClickListener {
            visibility()
        }

        binding?.updateNoteTitle?.setOnClickListener{
            visibility()
        }

        binding?.editButton?.setOnClickListener {

            binding?.updateDate?.text = SimpleDateFormat("MMMM dd, yyyy",
                Locale.getDefault()).format(Date())
            visibility()
        }

        binding?.saveToolbar?.setOnClickListener {
            updateNote(id)
        }

}

    private fun updateNote(id: Int){
        val title = binding?.updateNoteTitle?.text.toString()
        val content = binding?.updateContent?.text.toString()
        val date = binding?.updateDate?.text.toString()

        if(inputCheck(title, content, date)){
            //create note object
            val updatedNote = notes(id, title, content, date)
            //update current user
            mNoteViewModel.updateNote(updatedNote)

            Toast.makeText(this, "Note Updated!", Toast.LENGTH_LONG).show()

            //go back to main activity
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }else{
            Toast.makeText(applicationContext,
                "Note title or content cannot be empty",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, content: String, date: String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }

    private fun visibility(){
        binding?.saveToolbar?.visibility = View.VISIBLE
        binding?.editButton?.visibility = View.GONE
    }
}
