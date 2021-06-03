package com.mitoz.todo.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mitoz.todo.databinding.SingleItemBinding
import com.mitoz.todo.models.ModelsEntity

class AdaptersRecAdaptorDone(
    private var todosList : List<ModelsEntity>
) : RecyclerView.Adapter<AdaptersRecAdaptorDone.ViewHolder>() {


    inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)



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
                    TODO("buralar pasif")
                    this.expand = convertBooleanToInt(expand)
                    notifyDataSetChanged()
                }
                binding.checkBoxNot?.setOnCheckedChangeListener { buttonView, isChecked ->
                    val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
                    println(msg)
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