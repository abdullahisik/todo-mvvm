package com.mitoz.todo.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mitoz.todo.MainActivity
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.databinding.SingleItemBinding
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.viewmodels.ViewModelsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdaptersRecAdaptor  (private var todosList : List<ModelsEntity>) : RecyclerView.Adapter<AdaptersRecAdaptor.ViewHolder>()   {

    inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {



        }

    }
    private lateinit var db: DatabaseAppDatabase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        db = Room.databaseBuilder(parent.context, DatabaseAppDatabase::class.java, "todo-list.db").build()



        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){

            with(todosList[position]){
                val expand = convertIntToBoolean(this.expand)
                binding.tvLangName.text = this.title
                binding.tvDescription.text = this.description
                binding.imageViewExpand.setImageURI(Uri.parse(this.photograph))
                binding.textViewdateTime.text = this.datetime
                binding.expandedView.visibility = if (expand) View.VISIBLE else View.GONE
                binding.cardLayout.setOnClickListener {
                    this.expand = convertBooleanToInt(expand)
                    notifyDataSetChanged()
                }
                binding.checkBoxNot?.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        println(todosList[position])
                        GlobalScope.launch {
                            todosList[position].doneornot ="done"
                            db.todoDao().updateTodo(todosList[position])



                        }

                        notifyDataSetChanged()

                    }

                }
            }
        }
    }




    override fun getItemCount(): Int {
        return todosList.size
    }
    private fun convertIntToBoolean(int : Int) : Boolean{
        if(int == 0) {
            return false
        } else {
            return true
        }
    }
    private fun convertBooleanToInt(bool : Boolean) : Int{
        if(bool) {
            return 0
        } else {
            return 1
        }
    }
}