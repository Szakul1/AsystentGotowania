package com.example.asystentgotowania.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDatabase
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

        val dao = RecipeDatabase.getDatabase(this).recipeDao()
        // parsowanie intent
        val recipeList = when (intent.getIntExtra("searchType", 0)) {
            0 -> dao.getRecipeInclusive(intent.getStringExtra("recipeName")!!)
            1 -> dao.searchInclusiveByIngredients(intent.getStringArrayListExtra("ingredients")!!)
            else -> dao.searchExclusiveByIngredients(intent.getStringArrayListExtra("ingredients")!!)
        }

        val linearLayoutManager = ZoomRecyclerLayout(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_list)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = CustomAdapter(this, recipeList)
    }
}