package com.mitoz.todo.models

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ModelsDao  {


    @Query("SELECT * FROM todo_items")
    fun getAll(): List<ModelsEntity>
    @Query("SELECT * FROM todo_items WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<ModelsEntity>>
    @Insert
    fun insertAll(vararg todo: ModelsEntity)

    @Delete
    fun delete(todo: ModelsEntity)

    @Update
    fun updateTodo(vararg todos: ModelsEntity)
}