package com.mitoz.todo.ui

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.mitoz.todo.MainActivity
import com.mitoz.todo.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_ui_todo_entry.*
import java.util.*

class UiTodoEntryActivity : AppCompatActivity() {

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_todo_entry)
        supportActionBar?.apply {
            title = "Olacak"
            elevation = 15F


        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        textinputSchedule.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val date = "" + dayOfMonth.toString() + " " + month.toString() + ", " + year.toString()
                textinputSchedule.editText?.text =  date.toEditable()
            }, year, month, day)
            dpd.show()
        }




        buttonToolbarEntry.setOnClickListener {
            val inputTitle = textinpuTitle.editText?.text.toString()
            val inputDesction = textinpuTitle.editText?.text.toString()

            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)



        }









    }
}