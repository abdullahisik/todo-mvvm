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
                binding.tvLangName.text = this.name
                binding.tvDescription.text = this.description
                binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE
                binding.cardLayout.setOnClickListener {
                    this.expand = !this.expand
                    notifyDataSetChanged()
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return languageList.size
    }
}