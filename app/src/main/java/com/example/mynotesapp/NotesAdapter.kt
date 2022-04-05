package com.example.mynotesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.RecyclerviewItemBinding

class NotesAdapter(notesList: List<notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private var notesList : List<notes> = notesList


    inner class NotesViewHolder(private val itemBinding: RecyclerviewItemBinding)
        :RecyclerView.ViewHolder(itemBinding.root){

            var texttitle : TextView? = null
            var textdate : TextView? = null

            fun bindItem(){
                texttitle = itemBinding.noteTitle
                textdate = itemBinding.Date
            }

            fun setNote(notes : notes){
                texttitle?.text = notes.getTitle()
                textdate?.text = notes.getDate()

            }

        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding =  RecyclerviewItemBinding.inflate( LayoutInflater
            .from(parent.context), parent, false)

        return NotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.setNote(notesList[position])
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}