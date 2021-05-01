package com.example.asystentgotowania.db

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients(
    @Embedded val recipe: Recipe,
    @Relation(parentColumn = "title", entityColumn = "title")
    val ingredients: List<IngredientWithAmount>
)