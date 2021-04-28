package com.example.asystentgotowania.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.asystentgotowania.R


/**
 * Wyszukiwanie z list po nazwie
 */
class SearchByNameFragment(
    private var searchList: ArrayList<String>,
    private var ingredient: MutableLiveData<String>,
    private var removedSelected: MutableLiveData<String>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_search_by_name, container, false)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            root.context,
            android.R.layout.simple_dropdown_item_1line, searchList
        )
        val textView = root.findViewById<AutoCompleteTextView>(R.id.auto_complete)
        textView.threshold = 1
        textView.setAdapter(adapter)

        textView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val name = parent.getItemAtPosition(position).toString()
                ingredient.value = name
                adapter.remove(name)
                textView.setText("")
            }

        removedSelected.observe(viewLifecycleOwner, {item ->
            adapter.add(item)
        })

        return root
    }

}