package com.example.asystentgotowania.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.asystentgotowania.R
import com.google.android.flexbox.FlexboxLayout

class SearchFragment : Fragment() {

    private lateinit var root: View
    private lateinit var group: FlexboxLayout
    private var selected = ArrayList<String>()
    private lateinit var ingredients: ArrayList<String>
    private var removeSelected = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_search, container, false)
        group = root.findViewById(R.id.ingredients_group)

        val ingredient = MutableLiveData<String>()
        ingredient.observe(viewLifecycleOwner, Observer { item ->
            selected.add(item)
            addButton(item)
        })

        ingredients = ArrayList()
        ingredients.add("saowd")
        ingredients.add("pkamwd")
        ingredients.add("koanwd")

        val ingredientsSearch = SearchByNameFragment(ingredients, ingredient, removeSelected)
        val ft = childFragmentManager.beginTransaction()
        ft.add(R.id.search_ingredients, ingredientsSearch)
        ft.commit()

        return root
    }


    private fun addButton(name: String) {
        val button = LayoutInflater.from(context).inflate(R.layout.tag_view, null) as Button
        button.text = name
        button.setOnClickListener { view ->
            group.removeView(view)
            val buttonText = (view as Button).text.toString()
            selected.remove(buttonText)
            removeSelected.value = buttonText
        }
        group.addView(button)
    }

}