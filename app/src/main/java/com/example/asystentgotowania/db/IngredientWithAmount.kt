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
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)