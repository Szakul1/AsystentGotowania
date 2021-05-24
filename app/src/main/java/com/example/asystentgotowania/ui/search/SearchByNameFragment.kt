package com.example.asystentgotowania.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDatabase


/**
 * Wyszukiwanie z list po nazwie
 */
class SearchByNameFragment: Fragment() {

    private lateinit var ingredients: List<String>
    private val viewModel: IngredientsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search_by_name, container, false)

        ingredients =
            RecipeDatabase.getDatabase(requireContext()).recipeDao().loadAllIngredients()
                .map { it.name }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_dropdown_item_1line, ingredients
        )
        val textView = root.findViewById<AutoCompleteTextView>(R.id.auto_complete)
        textView.threshold = 1

        textView.setAdapter(adapter)
        textView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val name = parent.getItemAtPosition(position).toString()
                viewModel.ingredient.value = name
                adapter.remove(name)
                textView.setText("")
            }

        viewModel.removedSelected.observe(viewLifecycleOwner, { item ->
            adapter.add(item)
        })

        return root
    }

}