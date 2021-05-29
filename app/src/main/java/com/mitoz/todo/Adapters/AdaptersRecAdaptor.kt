package com.mitoz.todo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitoz.todo.models.ModelsModel
import com.mitoz.todo.databinding.SingleItemBinding

class AdaptersRecAdaptor(
    private var languageList: List<ModelsModel>
) : RecyclerView.Adapter<AdaptersRecAdaptor.ViewHolder>() {


    inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(languageList[position]){
                val expand = convertToIntBoolean(this.expand)
                binding.tvLangName.text = this.name
                binding.tvDescription.text = this.description
                binding.expandedView.visibility = if (expand) View.VISIBLE else View.GONE
                binding.cardLayout.setOnClickListener {
                    this.expand = convertBooleanToInt(expand)
                    notifyDataSetChanged()
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return languageList.size
    }
    private fun convertToIntBoolean(int : Int) : Boolean{
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