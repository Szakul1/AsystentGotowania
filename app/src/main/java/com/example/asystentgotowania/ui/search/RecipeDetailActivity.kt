package com.example.asystentgotowania.ui.search

import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.*
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDatabase
import com.example.asystentgotowania.db.IngredientWithAmount

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var timePicker: TimePicker
    private lateinit var counter: CountDownTimer
    private var endTime = 0L
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        supportActionBar?.hide()

        val dao = RecipeDatabase.getDatabase(this).recipeDao()
        val recipe = dao.getRecipe(intent.getStringExtra("recipeName")!!)

        findViewById<TextView>(R.id.name).text = recipe.title
        findViewById<TextView>(R.id.time).text = recipe.time
        findViewById<TextView>(R.id.size).text = recipe.size.toString()
        findViewById<TextView>(R.id.level).text = recipe.level
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        if (recipe.favorite)
            checkBox.isChecked = true
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                dao.setFavoriteOnRecipeByTitle(isChecked, recipe.title)
            } else {
                dao.setFavoriteOnRecipeByTitle(isChecked, recipe.title)
            }
        }

        val stream = assets.open(recipe.imageUrl)
        val bitMap = BitmapFactory.decodeStream(stream)
        findViewById<ImageView>(R.id.recipe_image).setImageBitmap(bitMap)

        val ingredients = dao.getRecipeByName(recipe.title)
        findViewById<TextView>(R.id.ingredients).text = createIngredients(ingredients)
        findViewById<TextView>(R.id.recipe).text = recipe.imageUrl.replace("KROK", "\nKROK")
        button = findViewById(R.id.start)

        timePicker = findViewById(R.id.timepicker)
        timePicker.setIs24HourView(true)
        timePicker.hour = 0
        timePicker.minute = 0
    }

    fun start(view: View) {
        val button = (view as Button)
        if (button.text == "Restart") {
            restartTimer(button)
            return
        }
        button.text = "Restart"
        val time = (timePicker.hour * 3600 + timePicker.minute * 60).toLong() * 1000
        endTime = System.currentTimeMillis() + time
        startTimer(time)
    }

    private fun startTimer(time: Long) {
        val con = this
        var first = true
        counter = object : CountDownTimer(
            time, 1000 * 60
        ) {
            override fun onTick(millisUntilFinished: Long) {
                if (first) {
                    first = false
                    return
                }
                if (timePicker.minute == 0) {
                    timePicker.hour--
                    timePicker.minute = 59
                } else
                    timePicker.minute--
            }

            override fun onFinish() {
                val not = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                val ring = RingtoneManager.getRingtone(applicationContext, not)
                ring.play()
                Handler(Looper.getMainLooper()).postDelayed( {
                    ring.stop()
                }, 5 * 1000)
                Toast.makeText(con, "Koniec", Toast.LENGTH_SHORT).show()
                restartTimer(button)
            }
        }.start()
    }

    private fun restartTimer(button: Button) {
        if (this::counter.isInitialized) {
            counter.cancel()
        }
        button.text = "Start"
        timePicker.hour = 0
        timePicker.minute = 0
        endTime = 0

    }

    private fun createIngredients(ingredients: List<IngredientWithAmount>): String {
        var stringList = ""
        for (i in ingredients) {
            stringList += "•   ${i.name} -- ${i.amount}\n"
        }
        return stringList
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("buttonText", button.text.toString())
        outState.putInt("hour", timePicker.hour)
        outState.putInt("minute", timePicker.minute)
        outState.putLong("endTime", endTime)
        if (endTime > 0) {
            counter.cancel()
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        timePicker.hour = savedInstanceState.getInt("hour")
        timePicker.minute = savedInstanceState.getInt("minute")
        button.text = savedInstanceState.getString("buttonText")
        endTime = savedInstanceState.getLong("endTime")
        if (endTime > 0)
            startTimer(endTime - System.currentTimeMillis())
    }

}