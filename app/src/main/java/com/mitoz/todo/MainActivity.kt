package com.mitoz.todo



import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.mitoz.todo.adapters.AdaptersRecAdaptor
import com.mitoz.todo.alarm.AlarmNotificationReciever
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.databinding.ActivityMainBinding
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.models.ModelsModel
import com.mitoz.todo.statics.StaticsContext
import com.mitoz.todo.ui.UiTodoEntryActivity
import com.mitoz.todo.viewmodels.ViewModelsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.coroutines.coroutineContext


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

getNotification("selam",applicationContext)
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


private fun scheduleNotification(notification: Notification, delay: Int) {
    val notificationIntent = Intent(StaticsContext.cntx, AlarmNotificationReciever::class.java)
    notificationIntent.putExtra(AlarmNotificationReciever.NOTIFICATION_ID, 1)
    notificationIntent.putExtra(AlarmNotificationReciever.NOTIFICATION, notification)
    val pendingIntent =
        PendingIntent.getBroadcast(StaticsContext.cntx, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    val futureInMillis = SystemClock.elapsedRealtime() + delay
    val alarmManager = StaticsContext.cntx.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
    alarmManager!![AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent

}

private fun getNotification(content: String, cntx : Context): Notification? {
    val builder = Notification.Builder(cntx)
    builder.setContentTitle("Scheduled Notification")
    builder.setContentText(content)
    builder.setSmallIcon(R.drawable.ic_launcher_background)

    return builder.build()
}


//private fun accesText() {
//    button.setOnClickListener {
//        viewModel.currentNumber.value = ++viewModel.number
//    }
//}