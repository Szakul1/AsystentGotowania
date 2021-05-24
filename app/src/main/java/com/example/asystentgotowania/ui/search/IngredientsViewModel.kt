package com.example.asystentgotowania.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IngredientsViewModel: ViewModel() {
    lateinit var ingredient: MutableLiveData<String>
    lateinit var removedSelected: MutableLiveData<String>
}