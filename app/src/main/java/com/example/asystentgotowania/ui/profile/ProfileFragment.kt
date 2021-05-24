package com.example.asystentgotowania.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentgotowania.R
import com.example.asystentgotowania.RecipeDao
import com.example.asystentgotowania.RecipeDatabase
import com.example.asystentgotowania.db.Recipe
import com.example.asystentgotowania.ui.search.CustomAdapter
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout
import java.util.*

class ProfileFragment : Fragment() {

    private lateinit  var recipeList: List<Recipe>
    private lateinit var adapter: CustomAdapter
    private lateinit var dao: RecipeDao

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

        dao = RecipeDatabase.getDatabase(view.context).recipeDao()
        recipeList = dao.getAllFavoriteRecipes()

        adapter = CustomAdapter(view.context, recipeList)
        recyclerView.adapter = adapter
        return view
    }

    override fun onResume() {
        getAdapterFromFragment()
        super.onResume()
    }

    private fun getAdapterFromFragment(){
        adapter.loadFromDatabase()
    }
}