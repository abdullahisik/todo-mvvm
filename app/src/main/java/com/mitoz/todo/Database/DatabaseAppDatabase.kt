package com.mitoz.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mitoz.todo.models.ModelsDao
import com.mitoz.todo.models.ModelsEntity

@Database(entities = arrayOf(ModelsEntity::class), version = 1)
abstract class DatabaseAppDatabase : RoomDatabase() {
    abstract fun todoDao(): ModelsDao
}