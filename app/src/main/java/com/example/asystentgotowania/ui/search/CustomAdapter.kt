package com.example.asystentgotowania.ui.search

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDatabase
import com.example.asystentgotowania.db.Recipe

class CustomAdapter(private val context: Context, private var dataSet: List<Recipe>) :
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
                intent.putExtra("recipeName", adapter.dataSet[adapterPosition].title)
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
        val name = if (dataSet[position].title.length > 40) {
            "${dataSet[position].title.subSequence(0, 40)}..."
        } else {
            dataSet[position].title
        }
        viewHolder.name.text = name
        viewHolder.time.text = dataSet[position].time
        viewHolder.size.text = dataSet[position].size.toString()
        viewHolder.level.text = dataSet[position].level
        val stream = context.assets.open(dataSet[position].imageUrl)
        val bitMap = BitmapFactory.decodeStream(stream)
        viewHolder.image.setImageBitmap(bitMap)
    }

    override fun getItemCount() = dataSet.size

    fun loadFromDatabase() {
        val dao = RecipeDatabase.getDatabase(context).recipeDao()
        dataSet = dao.getAllFavoriteRecipes()
        notifyDataSetChanged()
    }


}