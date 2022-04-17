package com.example.mynotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.mynotesapp.databinding.ActivityMainBinding
import com.example.mynotesapp.databinding.ActivityUpdatenoteBinding

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
    }


}