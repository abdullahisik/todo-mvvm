package com.mitoz.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mitoz.todo.ViewModels.ViewModelsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: ViewModelsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var a = "selam"
        accesText()
        Toast.makeText(this,a,Toast.LENGTH_LONG).show()
    viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)
viewModel.currentNumber.observe(this, Observer {
    textView.text = it.toString()
})
    }
  private fun accesText() {
      button.setOnClickListener {
          viewModel.currentNumber.value = ++viewModel.number
      }
  }
}