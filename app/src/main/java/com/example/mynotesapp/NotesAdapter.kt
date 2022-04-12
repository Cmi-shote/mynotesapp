package com.example.mynotesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.RecyclerviewItemBinding
import java.util.*
import kotlin.collections.ArrayList

//created a variable for the adapters list in the main adapter constructor
class NotesAdapter(private val items: ArrayList<notes>):
    RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    class ViewHolder(binding : RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){
        var noteTitle = binding.noteTitle
        var date = binding.Date

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val context = holder.itemView.context
        val item = items[position]

        holder.noteTitle.text = item.title
        holder.date.text = item.date
       // val note : notes = items[position]

    }

    override fun getItemCount(): Int {
        return items.size
    }
}


