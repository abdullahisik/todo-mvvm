package com.mitoz.todo.adapters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mitoz.todo.MainActivity
import com.mitoz.todo.alarm.AlarmNotificationReciever
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.databinding.SingleItemBinding
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.statics.StaticsContext.context
import com.mitoz.todo.ui.UiTodoEntryActivity
import com.mitoz.todo.viewmodels.ViewModelsViewModel
import kotlinx.android.synthetic.main.single_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AdaptersRecAdaptor(private var todosList: List<ModelsEntity>,callback : View.OnClickListener)
    : RecyclerView.Adapter<AdaptersRecAdaptor.ViewHolder>(), ViewModelStoreOwner {
    private lateinit var rvAdapter: AdaptersRecAdaptor
    private val mContext: Context? = null
    private val mCallback: View.OnClickListener? = null
    lateinit var viewModel: ViewModelsViewModel

    inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {


        }




    }
    private lateinit var db: DatabaseAppDatabase


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)
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
                binding.textViewdateTime.text = this.date+""+this.time
                binding.expandedView.visibility = if (expand) View.VISIBLE else View.GONE
                binding.imageView2.bringToFront()
                binding.imageView2.setOnClickListener { view ->
                    this.expand = convertBooleanToInt(expand)
                    notifyDataSetChanged()
                }
                binding.tvLangName.bringToFront()
                holder.itemView.setOnClickListener(){

                    val dintent = Intent(it.context, UiTodoEntryActivity::class.java)
                    val bundle = Bundle()
                    bundle.putInt("modelid",todosList[position].Id)
                    dintent.putExtras(bundle)
                    startActivity(it.context, dintent,bundle)

                }
                binding.checkBoxNot?.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        println(todosList[position])
                        GlobalScope.launch {
                            todosList[position].doneornot ="done"
                            db.todoDao().updateTodo(todosList[position])
                       }
                        val intentCloseAlarm = Intent(buttonView.context, AlarmNotificationReciever::class.java)
                        val pendingIntentCloseAlarm = PendingIntent.getBroadcast(
                            buttonView.context,
                            0,
                            intentCloseAlarm,
                            todosList[position].scheduleFlag
                        )
                        val am = buttonView.context.getSystemService(ALARM_SERVICE) as AlarmManager
                        if(pendingIntentCloseAlarm != null)
                        am.cancel(pendingIntentCloseAlarm)
                        val intentRelease = Intent(buttonView.context, MainActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("", "")
                        intentRelease.putExtras(bundle)
                        intentRelease.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intentRelease.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intentRelease.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        System.runFinalization()
                        Runtime.getRuntime().gc()
                        System.gc()
                        startActivity(buttonView.context, intentRelease,bundle)
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

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore()
    }

}