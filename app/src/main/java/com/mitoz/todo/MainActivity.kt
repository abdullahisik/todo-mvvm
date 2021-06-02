package com.mitoz.todo



import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
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


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    //private lateinit var todoDao: ModelsDao
    private lateinit var db: DatabaseAppDatabase
    private var todosList = ArrayList<ModelsEntity>()

    private lateinit var rvAdapter: AdaptersRecAdaptor
    lateinit var viewModel: ViewModelsViewModel
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        StaticsContext.cntx = applicationContext

        db = Room.databaseBuilder(applicationContext, DatabaseAppDatabase::class.java, "todo-list.db").build()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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

            getNotification("5 second delay","1.1")?.let { it1 -> scheduleNotification(it1, 5000,1) };
            getNotification("5 second delay","2.2")?.let { it2 -> scheduleNotification(it2, 10000,2) };





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





        })
    }
    private fun scheduleNotification(notification: Notification, delay: Int,flag : Int) {
        val notificationIntent = Intent(this, AlarmNotificationReciever::class.java)
        notificationIntent.putExtra(AlarmNotificationReciever.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(AlarmNotificationReciever.NOTIFICATION, notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            flag
        )
        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
    }
    private fun getNotification(content: String,id : String): Notification? {
        val mIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName, R.layout.activity_after_notification)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(id, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, id)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
        }
       // notificationManager.notify(1234, builder.build())

        return builder.build()
    }
}



//private fun accesText() {
//    button.setOnClickListener {
//        viewModel.currentNumber.value = ++viewModel.number
//    }
//}