package com.example.mynotesapp


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.RecyclerviewItemBinding
import java.util.*
import kotlin.collections.ArrayList


//created a variable for the adapters list in the main adapter constructor
class NotesAdapter(
    private var items: ArrayList<notes>,
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.noteTitle.text = item.title
        holder.date.text = item.date

        val code: Int = getRandomColor()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.parent.setBackgroundColor(holder.parent.resources.getColor(code, null))
        }



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


    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredNames: ArrayList<notes>) {
        this.items = filteredNames
        notifyDataSetChanged()
    }



}


