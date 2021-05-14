package com.example.asystentgotowania.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDatabase
import com.example.asystentgotowania.db.Recipe
import com.example.asystentgotowania.ui.search.CustomAdapter
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout
import java.util.*

class ProfileFragment : Fragment() {

    lateinit private var recipeList: List<Recipe>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val linearLayoutManager = ZoomRecyclerLayout(view.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_list)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.layoutManager = linearLayoutManager

        val dao = RecipeDatabase.getDatabase(view.context).recipeDao()
        recipeList = dao.getAllFavoriteRecipes()

        recyclerView.adapter = CustomAdapter(view.context, recipeList)
        return view
    }
}