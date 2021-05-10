package com.example.asystentgotowania.db

import androidx.room.Embedded
import androidx.room.Relation

data class IngredientWithRecipes(
    @Embedded val ingredient: IngredientWithAmount,
    @Relation(parentColumn = "title", entityColumn = "title")
    val recipes: List<Recipe>
)