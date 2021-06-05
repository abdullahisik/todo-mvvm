package com.mitoz.todo



import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.mitoz.todo.adapters.AdaptersRecAdaptor
import com.mitoz.todo.adapters.AdaptersRecAdaptorDone
import com.mitoz.todo.alarm.AlarmNotificationReciever
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.databinding.ActivityMainBinding
import com.mitoz.todo.models.ModelsEntity
import com.mitoz.todo.statics.StaticsContext
import com.mitoz.todo.statics.StaticsContext.context
import com.mitoz.todo.ui.UiTodoEntryActivity
import com.mitoz.todo.viewmodels.ViewModelsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.single_item.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    //private lateinit var todoDao: ModelsDao
    private lateinit var db: DatabaseAppDatabase
    private var todosList = ArrayList<ModelsEntity>()
    private var todosListDone = ArrayList<ModelsEntity>()

    private lateinit var rvAdapter: AdaptersRecAdaptor
    private lateinit var rvAdapterDone : AdaptersRecAdaptorDone
    lateinit var viewModel: ViewModelsViewModel
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"
    private val STORAGE_PERMISSION_CODE = 101

    @SuppressLint("RemoteViewLayout")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        StaticsContext.cntx = applicationContext
        val widthDp = resources.displayMetrics.run { widthPixels / density }
        val heightDp = resources.displayMetrics.run { heightPixels / density }
        not_constraint.maxHeight = (heightDp/2).toInt()-25
        //done_constraint.maxHeight = (heightDp/2).toInt()-25


        println("Yatay db : "+widthDp+" -Dikey dp : "+heightDp)
        db = Room.databaseBuilder(applicationContext, DatabaseAppDatabase::class.java, "todo-list.db").build()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        setupPermissions()
//        val data = db.todoDao().getAll()
       supportActionBar?.apply {
            title = "Olacak"
            elevation = 15F
        }
        buttonToolbar.setOnClickListener {

        val intent = Intent(applicationContext,UiTodoEntryActivity::class.java)
       startActivity(intent)
        }
        // add items to list
        rvAdapter = AdaptersRecAdaptor(todosList)
        binding.rvList.adapter = rvAdapter

        rvAdapter.notifyDataSetChanged()
        db.todoDao().findByTitle("done").observe(this, Observer {
          println("OLUYOR MU")
        })
        viewModel = ViewModelProvider(this).get(ViewModelsViewModel::class.java)

        viewModel.taskList.observe(this, Observer {
         it?.forEach {
                if(it.doneornot == "not") {
                    todosList.add(it)
                } else {
                    todosListDone.add(it)
                }
            }
                rvAdapter = AdaptersRecAdaptor(todosList)
                binding.rvList.adapter = rvAdapter
                rvAdapter.notifyDataSetChanged()
            rvAdapterDone = AdaptersRecAdaptorDone(todosListDone)
            binding.rvListDone.adapter = rvAdapterDone
            rvAdapterDone.notifyDataSetChanged()
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


    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (permission != PackageManager.PERMISSION_GRANTED) {

                makeRequest()
            }
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE)
    }
    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                setupPermissions()
                } else {

                }
            }
        }
    }
}