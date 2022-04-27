package com.example.mynotesapp.roomComponents


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.activities.UpdateNoteActivity
import com.example.mynotesapp.data.Notes
import com.example.mynotesapp.databinding.RecyclerviewItemBinding
import java.util.*
import kotlin.collections.ArrayList


//created a variable for the adapters list in the main adapter constructor
class NotesAdapter(
    private var items: ArrayList<Notes>,
    private val deleteListener: (id: Int) -> Unit

) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    class ViewHolder(binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var noteTitle = binding.noteTitle
        var date = binding.Date
        var content = binding.content
        var parent = binding.noteLayout

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    //In the onBindViewHolder function we have set the title, date and content to the title, content and date from the data list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.noteTitle.text = item.title
        holder.date.text = item.date
        holder.content.text = item.content

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.parent.setBackgroundColor(holder.parent.resources.getColor(item.color!!, null))
        }



        holder.parent.setOnClickListener {
            val intent = Intent(it.context, UpdateNoteActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("title", item.title)
            intent.putExtra("date", item.date)
            intent.putExtra("content", item.content)
            intent.putExtra("color", item.color)
            it.context.startActivity(intent)

        }

        holder.parent.setOnLongClickListener {
            deleteListener.invoke(item.id)
            return@setOnLongClickListener true
        }


    }





    override fun getItemCount(): Int {
        return items.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(note: List<Notes>){
        this.items = note as ArrayList<Notes>
        notifyDataSetChanged()
    }





}


