package com.mitoz.todo.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelsViewModel  : ViewModel(){

    var number = 0
    val currentNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }


 }