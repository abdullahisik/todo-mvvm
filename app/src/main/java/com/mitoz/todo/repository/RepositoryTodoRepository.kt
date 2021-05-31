package com.mitoz.todo.repository

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.models.ModelsDao
import com.mitoz.todo.models.ModelsEntity


class  RepositoryTodoRepository {
    private lateinit var todoDao: ModelsDao
    private lateinit var db: DatabaseAppDatabase
    private var todosList  = ArrayList<ModelsEntity>()
    public fun RepositoryTodoRepository(aplication : Application){
        db = Room.databaseBuilder(aplication, DatabaseAppDatabase::class.java, "todo-list.db").build()
        val data = db.todoDao().getAll()

    }
    fun insertAll(modelsEntity: ModelsEntity) {
        db.todoDao().insertAll(modelsEntity)

    }

    fun update(modelsEntity: ModelsEntity) {
        db.todoDao().updateTodo(modelsEntity)

    }

    fun delete(modelsEntity: ModelsEntity) {
        db.todoDao().delete(modelsEntity)
    }


    fun  getAll(): List<ModelsEntity> {
        val data = db.todoDao().getAll()
        return data
    }
}