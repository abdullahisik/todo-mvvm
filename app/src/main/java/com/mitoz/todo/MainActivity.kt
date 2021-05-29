package com.mitoz.todo


import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mitoz.todo.adapters.AdaptersRecAdaptor
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.models.ModelsDao
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.models.ModelsModel
import com.mitoz.todo.viewmodels.ViewModelsViewModel
import com.mitoz.todo.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    //private lateinit var todoDao: ModelsDao
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
                "urimuri",0
            ))


            val data = db.todoDao().getAll()

            data?.forEach {
                println(it)
            }
        }

        rvAdapter = AdaptersRecAdaptor(languageList)
        binding.rvList.adapter = rvAdapter

        val language1 = ModelsModel(
            0,
            "Java is an Object Oriented Programming language.",
            "java is running on 3 billion devices",54651211,"uri muri",0
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





//private fun accesText() {
//    button.setOnClickListener {
//        viewModel.currentNumber.value = ++viewModel.number
//    }
//}