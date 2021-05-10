package com.example.asystentgotowania

import androidx.room.*
import com.example.asystentgotowania.db.*

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredientWithAmount(ingredientWithAmount: IngredientWithAmount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Transaction
    @Query("SELECT * FROM recipe WHERE title LIKE '%' || :name || '%'")
    fun getRecipeWithIngredients(name: String): List<RecipeWithIngredients>
    
    @Transaction
    @Query("SELECT * FROM ingredientwithamount WHERE name LIKE '%' || :name || '%'")
    fun getRecipe(name: String): List<IngredientWithAmount>

    @Query("SELECT * FROM recipe")
    fun loadAllRecipes(): List<Recipe>

    @Query("SELECT * FROM ingredient")
    fun loadAllIngredients(): List<Ingredient>


}