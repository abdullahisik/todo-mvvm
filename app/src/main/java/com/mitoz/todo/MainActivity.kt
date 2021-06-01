package com.mitoz.todo


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mitoz.todo.adapters.AdaptersRecAdaptor
import com.mitoz.todo.statics.StaticsContext
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.models.ModelsModel
import com.mitoz.todo.viewmodels.ViewModelsViewModel
import com.mitoz.todo.databinding.ActivityMainBinding
import com.mitoz.todo.ui.UiTodoEntryActivity
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
        StaticsContext.cntx = applicationContext

        db = Room.databaseBuilder(applicationContext, DatabaseAppDatabase::class.java, "todo-list.db").build()

        db.todoDao().findByTitle("done").observe(this,Observer{
            it?.forEach {

                println("findbytitle")

                //  viewModel.taskList.value.add();
            }
        })




//        val data = db.todoDao().getAll()
//        data?.forEach {
//            todosList.add(it)
//            //  viewModel.taskList.value.add();
//        }

       // rvAdapter = AdaptersRecAdaptor(todosList)
        //binding.rvList.adapter = rvAdapter

        supportActionBar?.apply {
            title = "Olacak"
            elevation = 15F


        }
        buttonToolbar.setOnClickListener {

            val intent = Intent(applicationContext,UiTodoEntryActivity::class.java)
         startActivity(intent)
        }
        val language1 = ModelsModel(
            0,
            "Java is an Object Oriented Programming language.",
            "java is running on 3 billion devices",54651211,"uri muri",0
        )




        // add items to list
        rvAdapter = AdaptersRecAdaptor(todosList)
        binding.rvList.adapter = rvAdapter
        rvAdapter.notifyDataSetChanged()


        viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)
        viewModel.taskList.observe(this, Observer {


         it?.forEach {
                todosList.add(it)
            }
                rvAdapter = AdaptersRecAdaptor(todosList)
                binding.rvList.adapter = rvAdapter
                rvAdapter.notifyDataSetChanged()



            println("VİEW MODEL TASK LİST OBSERVE")

           // rvAdapter.notifyDataSetChanged()



        })
    }
}





//private fun accesText() {
//    button.setOnClickListener {
//        viewModel.currentNumber.value = ++viewModel.number
//    }
//}