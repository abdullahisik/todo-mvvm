package com.mitoz.todo.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitoz.todo.Models.ModelsModel

class ViewModelsViewModel  : ViewModel(){
    private var languageList = ArrayList<ModelsModel>()

    var number = 0
        val currentNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val taskList: MutableLiveData<ArrayList<ModelsModel>> by lazy {
        MutableLiveData<ArrayList<ModelsModel>>()
    }
}