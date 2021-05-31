package com.mitoz.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.mitoz.todo.R
import kotlinx.android.synthetic.main.activity_ui_todo_entry.*

class UiTodoEntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_todo_entry)




        val inputTitle = textinpuTitle.editText?.text.toString()
        val inputDesction = textinpuTitle.editText?.text.toString()





    }
}