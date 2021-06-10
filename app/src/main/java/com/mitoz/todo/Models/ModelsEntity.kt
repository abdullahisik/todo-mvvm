package com.mitoz.todo.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class ModelsEntity (
    @PrimaryKey(autoGenerate = true) var Id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "scheduleFlag") var scheduleFlag: Int,
    @ColumnInfo(name = "ctimemilis") var ctimemilis: Int,
    @ColumnInfo(name = "photograph") var photograph: String,
    @ColumnInfo(name = "expand") var expand: Int,
    @ColumnInfo(name = "doneornot") var doneornot: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time") var time: String




)