package com.mitoz.todo.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class ModelsEntity(
    @PrimaryKey(autoGenerate = true) var Id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "schedule") val schedule: Long,
    @ColumnInfo(name = "photograph") val photograph: String
    )