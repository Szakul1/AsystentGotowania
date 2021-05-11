package com.example.asystentgotowania.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentgotowania.R
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

/**
 * Aktywnosc z recyclerView
 */
@Suppress("UNCHECKED_CAST")
class RecipeListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        supportActionBar?.hide()

        val linearLayoutManager = ZoomRecyclerLayout(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.layoutManager = linearLayoutManager
        val recipeList = intent.getSerializableExtra("recipeList")
        recyclerView.adapter = CustomAdapter(recipeList as ArrayList<String>)
    }
}