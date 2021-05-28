package com.mitoz.todo


import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mitoz.todo.Adapters.AdaptersRecAdaptor
import com.mitoz.todo.Database.DatabaseAppDatabase
import com.mitoz.todo.Models.ModelsDao
import com.mitoz.todo.Models.ModelsEntity
import com.mitoz.todo.Models.ModelsModel
import com.mitoz.todo.ViewModels.ViewModelsViewModel
import com.mitoz.todo.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var todoDao: ModelsDao
    private lateinit var db: DatabaseAppDatabase
    private var languageList = ArrayList<ModelsModel>()
    private lateinit var rvAdapter: AdaptersRecAdaptor
    lateinit var viewModel: ViewModelsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        val icon = BitmapFactory.decodeResource(
            this.getResources(),
            R.drawable.ic_launcher_background
        )
        db = Room.databaseBuilder(this, DatabaseAppDatabase::class.java, "todo-list.db").build()
        GlobalScope.launch {
            db.todoDao().insertAll(ModelsEntity(
                0,
                "Çöpü at",
                "Bu akşam çöpleri atman gerek",
                2565481,
                "urimuri"
            ))

            val data = db.todoDao().getAll()

            data?.forEach {
                println(it)
            }
        }

insertData()
        rvAdapter = AdaptersRecAdaptor(languageList)
        binding.rvList.adapter = rvAdapter

        val language1 = ModelsModel(
            "Java",
            "Java is an Object Oriented Programming language." +
                    " Java is used in all kind of applications like Mobile Applications (Android is Java based), " +
                    "desktop applications, web applications, client server applications, enterprise applications and many more. ",
            false
        )




        // add items to list
        languageList.add(language1)
        rvAdapter.notifyDataSetChanged()



    viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)
        viewModel.currentNumber.observe(this, Observer {


})
        viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)
        viewModel.taskList.observe(this, Observer {
            rvAdapter.notifyDataSetChanged()
        })
    }

}

 private fun insertData() {


    }



//private fun accesText() {
//    button.setOnClickListener {
//        viewModel.currentNumber.value = ++viewModel.number
//    }
//}