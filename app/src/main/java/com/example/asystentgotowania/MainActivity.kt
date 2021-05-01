package com.example.asystentgotowania

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.asystentgotowania.db.Ingredient
import com.example.asystentgotowania.db.IngredientWithAmount
import com.example.asystentgotowania.db.Recipe
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        
        val dao = RecipeDatabase.getDatabase(this).recipeDao()

        val recipes = listOf(
            Recipe("Schabowe", "0:45", "sredni", 4),
            Recipe("Kurczak", "1:45", "latwy", 2),
            Recipe("Zupa jarzynowa", "2:30", "trudny", 6)
        )

        val ingredients = listOf(
            Ingredient("burak"),
            Ingredient("jajko"),
            Ingredient("kapusta"),
            Ingredient("czosnek"),
            Ingredient("ziemniak")
        )

        val ingredientsWithAmounts = listOf(
            IngredientWithAmount("czosnek", "Kurczak", "2 zabki"),
            IngredientWithAmount("ziemniak", "Kurczak", "300g"),
            IngredientWithAmount("burak", "Zupa jarzynowa", "4"),
            IngredientWithAmount("kapusta", "Schabowe", "1 glowka")
        )

        lifecycleScope.launch {
            recipes.forEach { dao.insertRecipe(it) }
            ingredients.forEach { dao.insertIngredient(it) }
            ingredientsWithAmounts.forEach { dao.insertIngredientWithAmount(it) }

            val recipeWithIngredients = dao.getRecipeWithIngredients("Kurczak")
            recipeWithIngredients[0].ingredients.forEach { Log.d("Mytag", it.name) }
        }
    }
}