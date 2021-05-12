package com.example.asystentgotowania

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.*

class MainActivity : AppCompatActivity() {

    lateinit var dao: RecipeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        dao = RecipeDatabase.getDatabase(this).recipeDao()

//        val recipes = listOf(
//            Recipe("Schabowe", "0:45", "sredni", 4,"",""),
//            Recipe("Kurczak", "1:45", "latwy", 2,"",""),
//            Recipe("Zupa jarzynowa", "2:30", "trudny", 6,"","")
//        )
//
//        val ingredients = listOf(
//            Ingredient("burak"),
//            Ingredient("jajko"),
//            Ingredient("kapusta"),
//            Ingredient("czosnek"),
//            Ingredient("ziemniak")
//        )
//
//        val ingredientsWithAmounts = listOf(
//            IngredientWithAmount("czosnek", "Kurczak", "2 zabki"),
//            IngredientWithAmount("ziemniak", "Kurczak", "300g"),
//            IngredientWithAmount("burak", "Zupa jarzynowa", "4"),
//            IngredientWithAmount("kapusta", "Schabowe", "1 glowka"),
//            IngredientWithAmount("ziemniak", "Schabowe", "500g")
//        )
////
//        lifecycleScope.launch {
//            recipes.forEach { dao.insertRecipe(it) }
//            ingredients.forEach { dao.insertIngredient(it) }
//            ingredientsWithAmounts.forEach { dao.insertIngredientWithAmount(it) }
//        }
        Thread {
            Log.d("siema", dao.loadAllRecipes().size.toString())
        }.start()
    }

    fun searchByIngredients(ingredients: List<String>) {
        var flag = true
        var previousRecipes = mutableListOf<String>()
        for (ingredient in ingredients) {
            var recipeWithIngredients = dao.getRecipe("ziemniak")
            var recipes = mutableListOf<String>()
            recipeWithIngredients.forEach { recipes.add(it.title) }
            if (!flag) {
                previousRecipes.intersect(recipes)
            } else {
                flag = false
            }
        }
    }

}