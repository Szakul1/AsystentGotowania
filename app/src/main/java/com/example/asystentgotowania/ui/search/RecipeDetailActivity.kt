package com.example.asystentgotowania.ui.search

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDatabase
import com.example.asystentgotowania.db.IngredientWithAmount
import com.example.asystentgotowania.db.RecipeWithIngredients

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        supportActionBar?.hide()

        val dao = RecipeDatabase.getDatabase(this).recipeDao()
        val recipe = dao.getRecipe(intent.getStringExtra("recipeName")!!)

        findViewById<TextView>(R.id.name).text = recipe.title
        findViewById<TextView>(R.id.time).text = recipe.time
        findViewById<TextView>(R.id.size).text = recipe.size.toString()
        findViewById<TextView>(R.id.level).text = recipe.level
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        if (!recipe.favorite) {
            checkBox.setButtonDrawable(android.R.drawable.star_big_off)
        } else {
            checkBox.setButtonDrawable(android.R.drawable.star_big_on)
        }
        val stream = assets.open(recipe.imageUrl)
        val bitMap = BitmapFactory.decodeStream(stream)
        findViewById<ImageView>(R.id.recipe_image).setImageBitmap(bitMap)

        val ingredients = dao.getRecipeByName(recipe.title)
        findViewById<TextView>(R.id.ingredients).text = createIngredients(ingredients)
        findViewById<TextView>(R.id.recipe).text = recipe.recipe.replace("KROK", "\nKROK")

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkBox.setButtonDrawable(android.R.drawable.star_big_on)
                dao.setFavoriteOnRecipeByTitle(isChecked, recipe.title)
            } else {
                checkBox.setButtonDrawable(android.R.drawable.star_big_off)
                dao.setFavoriteOnRecipeByTitle(isChecked, recipe.title)
            }
        }
    }

    private fun createIngredients(ingredients: List<IngredientWithAmount>): String {
        var stringList = ""
        for (i in ingredients) {
            stringList += "•   ${i.name} -- ${i.amount}\n"
        }
        return stringList
    }
}