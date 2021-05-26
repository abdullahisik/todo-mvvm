package com.mitoz.todo.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitoz.todo.Models.ModelsModel

class ViewModelsViewModel  : ViewModel(){

    var number = 0
        val currentNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val taskList: MutableLiveData<ModelsModel> by lazy {
        MutableLiveData<ModelsModel>()
    }
}