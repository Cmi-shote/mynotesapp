package com.example.mynotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mynotesapp.databinding.ActivityEditNoteBinding
import com.example.mynotesapp.databinding.ActivityNewNoteBinding

class EditNote : AppCompatActivity() {
    private var binding : ActivityEditNoteBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

    }
}