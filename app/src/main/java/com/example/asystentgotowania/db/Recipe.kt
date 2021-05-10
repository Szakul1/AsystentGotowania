package com.example.asystentgotowania.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe (
    @PrimaryKey(autoGenerate = false)
    var title: String,
    @ColumnInfo(name = "time") var time: String,
    @ColumnInfo(name = "level") var level: String,
    @ColumnInfo(name = "size") var size: Int,
    @ColumnInfo(name = "imageUrl") var imageUrl: String
)