package com.example.asystentgotowania.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentgotowania.R

class CustomAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View, adapter: CustomAdapter) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val time: TextView = view.findViewById(R.id.time)
        val size: TextView = view.findViewById(R.id.size)
        val level: TextView = view.findViewById(R.id.level)
        val image: ImageView = view.findViewById(R.id.recipe_image)

        init {
            view.setOnClickListener {
                val intent = Intent(view.context, RecipeDetailActivity::class.java)
                intent.putExtra("recipe", adapter.dataSet[adapterPosition])
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_view_item, viewGroup, false)

        return ViewHolder(view, this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //TODO
    }

    override fun getItemCount() = dataSet.size

}