package com.example.asystentgotowania.ui.search

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.asystentgotowania.R

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        supportActionBar?.hide()
        //TODO parse recipe from intent
        findViewById<TextView>(R.id.name)
        findViewById<TextView>(R.id.time)
        findViewById<TextView>(R.id.size)
        findViewById<TextView>(R.id.level)
        var e = findViewById<ImageView>(R.id.recipe_image)

        findViewById<TextView>(R.id.ingredients).text = createIngredients(arrayListOf())
        findViewById<TextView>(R.id.recipe)
    }

    private fun createIngredients(list: ArrayList<String>): String {
        var stringList = ""
        for (i in list) {
            stringList += "•   $list\n"
        }
        return stringList
    }
}