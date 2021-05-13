package com.example.asystentgotowania.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDao
import com.example.asystentgotowania.RecipeDatabase
import com.example.asystentgotowania.db.Recipe
import com.google.android.flexbox.FlexboxLayout

/**
 * Kazdy rodzaj wyszukiwania
 */
class SearchFragment : Fragment() {

    private lateinit var root: View
    private lateinit var group: FlexboxLayout
    private var selected = ArrayList<String>()
    private var removeSelected = MutableLiveData<String>()
    private lateinit var dao: RecipeDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_search, container, false)
        dao = RecipeDatabase.getDatabase(requireContext()).recipeDao()
        group = root.findViewById(R.id.ingredients_group)

        val ingredient = MutableLiveData<String>()
        ingredient.observe(viewLifecycleOwner, Observer { item ->
            selected.add(item)
            addButton(item)
        })

        val ingredientsSearch = SearchByNameFragment(ingredient, removeSelected)
        val ft = childFragmentManager.beginTransaction()
        ft.add(R.id.search_ingredients, ingredientsSearch)
        ft.commit()

        root.findViewById<Button>(R.id.whatCanDo).setOnClickListener { whatCanDo() }
        root.findViewById<Button>(R.id.whatWantToEat).setOnClickListener { whatWantToEat() }
        root.findViewById<Button>(R.id.search_by_name_button).setOnClickListener { searchByName() }
        return root
    }

    private fun searchByName() {
        val intent = Intent(context, RecipeListActivity::class.java)
        val recipeName = root.findViewById<EditText>(R.id.search_by_name).text.toString()
        intent.putExtra("recipeName", recipeName)
        intent.putExtra("searchType", 0)
        startActivity(intent)
    }

    private fun whatWantToEat() {
        val intent = Intent(context, RecipeListActivity::class.java)
        intent.putExtra("ingredients", selected)
        intent.putExtra("searchType", 1)
        startActivity(intent)
    }

    private fun whatCanDo() {
        val intent = Intent(context, RecipeListActivity::class.java)
        intent.putExtra("ingredients", selected)
        intent.putExtra("searchType", 2)
        startActivity(intent)
    }


    /**
     * Dodawanie tagow
     */
    private fun addButton(name: String) {
        val button = LayoutInflater.from(context).inflate(R.layout.tag_view, null) as Button
        button.apply {
            requestLayout()
            text = name
            setOnClickListener { view ->
                group.removeView(view)
                val buttonText = (view as Button).text.toString()
                selected.remove(buttonText)
                removeSelected.value = buttonText
            }
        }
        group.addView(button)
    }

}