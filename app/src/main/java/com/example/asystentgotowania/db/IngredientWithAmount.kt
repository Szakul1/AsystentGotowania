package com.example.asystentgotowania.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity=Ingredient::class,
                            parentColumns = arrayOf("name"),
                            childColumns = arrayOf("name"),
                            onDelete = ForeignKey.CASCADE)))
data class IngredientWithAmount(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "amount") var amount: String
)