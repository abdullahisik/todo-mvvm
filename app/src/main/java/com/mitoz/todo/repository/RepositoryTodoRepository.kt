package com.mitoz.todo.repository

import android.content.Context
import androidx.room.Room
import com.mitoz.todo.statics.StaticsContext
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.models.ModelsDao
import com.mitoz.todo.models.ModelsEntity


class  RepositoryTodoRepository {
    private lateinit var todoDao: ModelsDao
    private lateinit var db: DatabaseAppDatabase
    private var todosList  = ArrayList<ModelsEntity>()


    init{
        db = Room.databaseBuilder(StaticsContext.cntx, DatabaseAppDatabase::class.java, "todo-list.db").build()
        val data = db.todoDao().getAll()
    }
    public fun RepositoryTodoRepository(context : Context){


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