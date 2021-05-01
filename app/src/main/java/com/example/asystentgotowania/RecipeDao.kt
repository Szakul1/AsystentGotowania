package com.example.asystentgotowania

import androidx.room.*
import com.example.asystentgotowania.db.Ingredient
import com.example.asystentgotowania.db.IngredientWithAmount
import com.example.asystentgotowania.db.Recipe
import com.example.asystentgotowania.db.RecipeWithIngredients

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredientWithAmount(ingredientWithAmount: IngredientWithAmount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Transaction
    @Query("SELECT * FROM recipe WHERE title = :name")
    suspend fun getRecipeWithIngredients(name: String): List<RecipeWithIngredients>
}