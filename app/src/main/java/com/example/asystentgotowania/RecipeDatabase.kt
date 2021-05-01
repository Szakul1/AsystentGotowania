package com.example.asystentgotowania

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asystentgotowania.db.Ingredient
import com.example.asystentgotowania.db.IngredientWithAmount
import com.example.asystentgotowania.db.Recipe
import com.example.asystentgotowania.db.RecipeWithIngredients

@Database(entities = [
    Recipe::class,
    Ingredient::class,
    IngredientWithAmount::class
    ], version=1, exportSchema=false)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}