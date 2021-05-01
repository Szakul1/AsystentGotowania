package com.example.asystentgotowania.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class Ingredient (
    @PrimaryKey(autoGenerate = false)
    var name: String
)