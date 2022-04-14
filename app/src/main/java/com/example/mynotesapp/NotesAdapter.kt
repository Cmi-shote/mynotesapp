package com.example.mynotesapp



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.RecyclerviewItemBinding
import java.util.*
import kotlin.collections.ArrayList

//created a variable for the adapters list in the main adapter constructor
class NotesAdapter(private var items: ArrayList<notes>,
                   private val updateListener:(id:Int)->Unit,
                   private val deleteListener:(id:Int)->Unit


): RecyclerView.Adapter<NotesAdapter.ViewHolder>(){


    class ViewHolder(binding : RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root){
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.noteTitle.text = item.title
        holder.date.text = item.date
       // holder.content.text = item.content

        holder.parent.setOnClickListener {
            updateListener.invoke(item.id)
        }

      holder.parent.setOnLongClickListener {
            deleteListener.invoke(item.id)
            return@setOnLongClickListener true
        }



    }




    override fun getItemCount(): Int {
        return items.size
    }

    fun getRandomColor(): Int {
        val colorCode : ArrayList<Int>? = null
        colorCode?.add(R.color.ORANGE)
        colorCode?.add(R.color.blue)
        colorCode?.add(R.color.lemon)
        colorCode?.add(R.color.pink)
        colorCode?.add(R.color.purplr)
        colorCode?.add(R.color.salmon)

        val randomColor = Random()
        val number = randomColor.nextInt(colorCode!!.size)


        return colorCode[number]
    }


}


