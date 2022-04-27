package com.example.mynotesapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mynotesapp.R
import com.example.mynotesapp.data.Notes
import com.example.mynotesapp.roomComponents.NoteViewModel
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

        if (inputCheck(title,content)){
            val note = Notes(0, title, content, date, getRandomColor())

            mNoteViewModel.insertData(note)

            Toast.makeText(this,
                "Note Added",
                Toast.LENGTH_SHORT).show()

            //closing the activity
            finish()

        }else {
            Toast.makeText(this,
                "Note title or content cannot be empty",
                Toast.LENGTH_SHORT).show()
            }
        }


    private fun getRandomColor(): Int {
        val colorCode: MutableList<Int> = ArrayList()
        colorCode.add(R.color.ORANGE)
        colorCode.add(R.color.blue)
        colorCode.add(R.color.yellow)
        colorCode.add(R.color.pink)
        colorCode.add(R.color.purplr)
        colorCode.add(R.color.salmon)
        colorCode.add(R.color.green)


        val randomColor = Random()
        val number = randomColor.nextInt(colorCode.size)
        return colorCode[number]


    }

    private fun inputCheck(title: String, content: String): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(content))
    }

}
