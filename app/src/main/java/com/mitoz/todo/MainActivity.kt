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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    //private lateinit var todoDao: ModelsDao
    private lateinit var db: DatabaseAppDatabase
    private var todosList = ArrayList<ModelsEntity>()

    private lateinit var rvAdapter: AdaptersRecAdaptor
    lateinit var viewModel: ViewModelsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvList.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this, DatabaseAppDatabase::class.java, "todo-list.db").build()

        db.todoDao().findByTitle("done").observe(this,Observer{
            it?.forEach {
                

                //  viewModel.taskList.value.add();
            }
        })
        GlobalScope.launch {
            db.todoDao().insertAll(ModelsEntity(
                0,
                "Çöpü at",
                "Bu akşam çöpleri atman gerek",
                2565481,
                "urimuri",0,"not"
            ))


            val data = db.todoDao().getAll()

            data?.forEach {
                if(it.doneornot == "not")
                        todosList.add(it)
                //  viewModel.taskList.value.add();
            }
        }




        rvAdapter = AdaptersRecAdaptor(todosList)
        binding.rvList.adapter = rvAdapter

        val language1 = ModelsModel(
            0,
            "Java is an Object Oriented Programming language.",
            "java is running on 3 billion devices",54651211,"uri muri",0
        )




        // add items to list

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