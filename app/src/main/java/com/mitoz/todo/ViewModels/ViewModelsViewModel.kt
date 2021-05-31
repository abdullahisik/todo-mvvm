package com.mitoz.todo.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitoz.todo.models.ModelsModel

class ViewModelsViewModel  : ViewModel(){


    private var todosList  = ArrayList<ModelsModel>()

    var number = 0
        val currentNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val taskList: MutableLiveData<ArrayList<ModelsModel>> by lazy {
        MutableLiveData<ArrayList<ModelsModel>>()
    }
}