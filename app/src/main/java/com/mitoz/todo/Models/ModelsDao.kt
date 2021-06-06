package com.mitoz.todo.models

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ModelsDao  {


    @Query("SELECT * FROM todo_items")
    fun getAll(): List<ModelsEntity>
    @Query("SELECT * FROM todo_items WHERE doneornot LIKE :doneornot")
    fun findByTitle(doneornot: String): LiveData<List<ModelsEntity>>
    @Insert
    fun insertAll(vararg todo: ModelsEntity)

    @Delete
    fun delete(todo: ModelsEntity)

    @Update
    fun updateTodo(vararg todos: ModelsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(todo: ModelsEntity)
    @Query("SELECT * FROM todo_items WHERE Id LIKE :mId")
    fun getItem(mId : Int): ModelsEntity
}