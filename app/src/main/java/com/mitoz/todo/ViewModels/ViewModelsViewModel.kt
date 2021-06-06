package com.mitoz.todo.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.repository.RepositoryTodoRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ViewModelsViewModel  : ViewModel(){

    private lateinit var repository: RepositoryTodoRepository
    private var todosList = ArrayList<ModelsEntity>()
    val taskList: MutableLiveData<ArrayList<ModelsEntity>> by lazy {
        MutableLiveData<ArrayList<ModelsEntity>>()
    }
    init {
        GlobalScope.launch {
            repository = RepositoryTodoRepository()
            val data =   repository.getAll()
            data?.forEach {
                    todosList.add(it)
                //  viewModel.taskList.value.add();
            }
            taskList.postValue(todosList)
        }


    }

    fun insert(modelsentity: ModelsEntity) {
        repository.insertAll(modelsentity)
    }

    fun update(modelsentity: ModelsEntity) {
        repository.update(modelsentity)
    }

    fun delete(modelsentity: ModelsEntity) {
        repository.delete(modelsentity)
    }



    fun getAllTodos(): ArrayList<ModelsEntity> {
        return todosList
    }






    val number = 0
    
        val currentNumber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

}