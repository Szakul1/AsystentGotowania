package com.example.asystentgotowania

import androidx.room.*
import com.example.asystentgotowania.db.*

@Dao
abstract class RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipe(recipe: Recipe)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertIngredientWithAmount(ingredientWithAmount: IngredientWithAmount)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertIngredient(ingredient: Ingredient)

    @Transaction
    @Query("SELECT * FROM recipe WHERE title LIKE '%' || :name || '%'")
    abstract fun getRecipeWithIngredients(name: String): List<RecipeWithIngredients>

    @Transaction
    @Query("SELECT * FROM ingredientwithamount WHERE name LIKE '%' || :name || '%'")
    abstract fun getRecipeByIngredient(name: String): List<IngredientWithAmount>

    @Query("SELECT * FROM recipe")
    abstract fun loadAllRecipes(): List<Recipe>

    @Query("SELECT * FROM ingredient")
    abstract fun loadAllIngredients(): List<Ingredient>

    @Query("SELECT * FROM recipe WHERE title = :title")
    abstract fun getRecipe(title: String): Recipe

    @Query("SELECT * FROM recipe")
    abstract fun getAllRecipeWithIngredients(): List<RecipeWithIngredients>

    fun searchByIngredients(ingredients: List<String>): MutableList<Recipe> {
        var flag = true
        var previousRecipes = mutableListOf<Recipe>()
        for (ingredient in ingredients) {
            val recipeWithIngredients = getRecipeByIngredient(ingredient)
            val recipes = mutableListOf<Recipe>()
            recipeWithIngredients.forEach { recipes.add(getRecipe(it.title)) }
            if (!flag) {
                previousRecipes.intersect(recipes)
            } else {
                flag = false
                previousRecipes = recipes
            }
        }
        return previousRecipes
    }

    fun searchExclusiveByIngredients (ingredients: List<String>): MutableList<Recipe> {
        val allRecipes = getAllRecipeWithIngredients()
        val recipes = mutableListOf<Recipe>()
        for (i in allRecipes) {
            if (ingredients.containsAll(i.ingredients.map { it.name }))
                recipes.add(i.recipe)
        }
        return recipes
    }

    fun searchInclusiveByIngredients (ingredients: List<String>): MutableList<Recipe> {
        val allRecipes = getAllRecipeWithIngredients()
        val recipes = mutableListOf<Recipe>()
        for (i in allRecipes) {
            if (i.ingredients.map { it.name }.containsAll(ingredients))
                recipes.add(i.recipe)
        }
        return recipes
    }

}