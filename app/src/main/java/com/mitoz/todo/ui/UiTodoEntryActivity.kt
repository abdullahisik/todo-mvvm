package com.mitoz.todo.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
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


        textinputSchedule.setOnClickListener {


        }




        buttonToolbarEntry.setOnClickListener {
            val inputTitle = textinpuTitle.editText?.text.toString()
            val inputDesction = textinpuTitle.editText?.text.toString()

            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)



        }









    }


    fun onclickSheduleDate(view: View) {
        println("olur gibi")
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in TextView
            val date = "" + dayOfMonth.toString() + ", " + month.toString() + ", " + year.toString()
            textinputSchedule.editText?.text =  date.toEditable()
        }, year, month, day)
        dpd.show()
    }

    fun onclickTime(view: View) {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                val time = hourOfDay.toString()+":"+minute.toString()
                textinputTime.editText?.text = time.toEditable()
            }
        }, hour, minute, false)
mTimePicker.show()
    }
}