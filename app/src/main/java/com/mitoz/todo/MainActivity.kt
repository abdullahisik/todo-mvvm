package com.mitoz.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mitoz.todo.Adapters.AdaptorsRecAdaptor
import com.mitoz.todo.Models.ModelsModel
import com.mitoz.todo.ViewModels.ViewModelsViewModel
import com.mitoz.todo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var languageList = ArrayList<ModelsModel>()
    private lateinit var rvAdapter: AdaptorsRecAdaptor
    lateinit var viewModel: ViewModelsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.layoutManager = LinearLayoutManager(this)

        rvAdapter = AdaptorsRecAdaptor(languageList)
        binding.rvList.adapter = rvAdapter


        val language1 = ModelsModel(
            "Java",
            "Java is an Object Oriented Programming language." +
                    " Java is used in all kind of applications like Mobile Applications (Android is Java based), " +
                    "desktop applications, web applications, client server applications, enterprise applications and many more. ",
            false
        )
        val language2 = ModelsModel(
            "Kotlin",
            "Kotlin is a statically typed, general-purpose programming language" +
                    " developed by JetBrains, that has built world-class IDEs like IntelliJ IDEA, PhpStorm, Appcode, etc.",
            false
        )
        val language3 = ModelsModel(
            "Python",
            "Python is a high-level, general-purpose and a very popular programming language." +
                    " Python programming language (latest Python 3) is being used in web development, Machine Learning applications, " +
                    "along with all cutting edge technology in Software Industry.",
            false
        )
        val language4 = ModelsModel(
            "CPP",
            "C++ is a general purpose programming language and widely used now a days for " +
                    "competitive programming. It has imperative, object-oriented and generic programming features. ",
            false
        )

        // add items to list
        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)
        languageList.add(language4)

        rvAdapter.notifyDataSetChanged()



    viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)
        viewModel.currentNumber.observe(this, Observer {


})
    }

}
//  private fun accesText() {
//      button.setOnClickListener {
//          viewModel.currentNumber.value = ++viewModel.number
//      }
//  }