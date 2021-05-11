package com.example.asystentgotowania.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.asystentgotowania.R

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        supportActionBar?.hide()
        //TODO parse recipe from intent
    }
}