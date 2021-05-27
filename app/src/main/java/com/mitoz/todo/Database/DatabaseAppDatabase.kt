package com.mitoz.todo.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mitoz.todo.Models.ModelsDao
import com.mitoz.todo.Models.ModelsEntity

@Database(entities = arrayOf(ModelsEntity::class), version = 1)
abstract class DatabaseAppDatabase : RoomDatabase() {
    abstract fun todoDao(): ModelsDao
}