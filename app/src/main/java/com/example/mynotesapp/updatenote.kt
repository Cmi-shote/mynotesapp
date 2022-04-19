package com.example.mynotesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.example.mynotesapp.databinding.ActivityUpdatenoteBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class updatenote : AppCompatActivity() {
    private var binding: ActivityUpdatenoteBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdatenoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.backButton?.setOnClickListener {
            onBackPressed()
        }

        var data = intent

        binding?.updateNoteTitle?.setText(data.getStringExtra("title"))
        binding?.updateDate?.text = data.getStringExtra("date")

        binding?.editButton?.setOnClickListener {

            binding?.updateDate?.text = SimpleDateFormat("MMMM dd, yyyy",
                Locale.getDefault()).format(Date())
            binding?.saveToolbar?.visibility = View.VISIBLE
            binding?.editButton?.visibility = View.GONE
        }
        val notedao = (application as NotesApp).db.notesDao()

        binding?.saveToolbar?.setOnClickListener {
            update(notedao)
        }

}

    private fun update(notedao : notesDao){
        val title = binding?.updateNoteTitle?.text.toString()
        val date = binding?.updateDate?.text.toString()
        val content = binding?.updateContent?.text.toString()

        if (title.isEmpty() || content.isEmpty()){
            Toast.makeText(applicationContext,
                "Note title or content cannot be empty",
                Toast.LENGTH_SHORT).show()
        }else {
            lifecycleScope.launch {
                notedao.insert(notes(0, title, date))
                Toast.makeText(
                    applicationContext,
                    "Note updated",
                    Toast.LENGTH_SHORT
                ).show()


            }
        }

        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun deleteNote(id: Int, nodedao: notesDao) {

        lifecycleScope.launch {
            nodedao.delete(notes(id))
            Toast.makeText(
                applicationContext,
                "Note deleted.",
                Toast.LENGTH_SHORT
            ).show()

        }


    }


}
