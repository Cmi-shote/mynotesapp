package com.example.mynotesapp



import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.RecyclerviewItemBinding
import java.util.*
import kotlin.collections.ArrayList

//created a variable for the adapters list in the main adapter constructor
class NotesAdapter(private var items: ArrayList<notes>,
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

        //holder.parent.setBackgroundColor(holder.itemView.resources.getColor(getRandomColor()))

        holder.parent.setOnClickListener {
            var intent = Intent(it.context, updatenote::class.java)
            intent.putExtra("title", item.title)
            intent.putExtra("date", item.date)
            it.context.startActivity(intent)
        }

      holder.parent.setOnLongClickListener {
            deleteListener.invoke(item.id)
            return@setOnLongClickListener true
        }



    }

    /*
    private fun getRandomColor(): Int {
            val colorCode : ArrayList<Int>? = null
            colorCode?.add(R.color.ORANGE, 1)
            colorCode?.add(R.color.blue, 2)
            colorCode?.add(R.color.lemon, 3)
            colorCode?.add(R.color.pink, 4)
            colorCode?.add(R.color.purplr, 5)
            colorCode?.add(R.color.salmon, 6)

            val randomColor = Random()
            val number : Int = randomColor.nextInt(colorCode!!.size)


            return colorCode[number]

    }


     */

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredNames: ArrayList<notes>){
        this.items = filteredNames
        notifyDataSetChanged()
    }




}


