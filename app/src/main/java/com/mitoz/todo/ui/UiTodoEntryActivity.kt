package com.mitoz.todo.ui

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TimePicker
import android.widget.Toast
import androidx.room.Room
import com.mitoz.todo.MainActivity
import com.mitoz.todo.R
import com.mitoz.todo.alarm.AlarmNotificationReciever
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.models.ModelsEntity
import kotlinx.android.synthetic.main.activity_ui_todo_entry.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class UiTodoEntryActivity : AppCompatActivity() {

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var imageView: ImageView
    private lateinit var db: DatabaseAppDatabase
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications21"
    private val description = "deneme"
    private var calendarMilis : Int = 0
    val c = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_todo_entry)
        imageView = findViewById(R.id.imageView)

        supportActionBar?.apply {
            title = "Olacak"
            elevation = 15F


        }
        db = Room.databaseBuilder(this, DatabaseAppDatabase::class.java, "todo-list.db").build()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



buttonBrowseFiles.setOnClickListener(){
    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    startActivityForResult(gallery, pickImage)
}


        buttonToolbarEntry.setOnClickListener {

            val inputTitle = textinpuTitle.editText?.text.toString()
            val inputDesction = textinputDescription.editText?.text.toString()
            calendarMilis = c.timeInMillis.toInt() - System.currentTimeMillis().toInt()
            println(calendarMilis.toString())

            if (inputTitle.isNullOrEmpty() and inputDesction.isNullOrEmpty()) {
                Toast.makeText(this,"Görev ismi ve açıklama girmek zorunludur !",Toast.LENGTH_LONG).show()


                // alerts the user to fill in their number!
        } else {
                if(calendarMilis>0)
                {
                    getNotification("5 second delay","1.1.2")?.let { it1 -> scheduleNotification(it1, calendarMilis,calendarMilis)};
                } else {
                    calendarMilis = 0
                }
                GlobalScope.launch {
                    db.todoDao().insertAll(
                        ModelsEntity(
                            0,
                            inputTitle,
                            inputDesction,
                            calendarMilis,
                            imageUri.toString(),0,"not"
                        )
                    )}
//                val intent = Intent(applicationContext,MainActivity::class.java)
//                startActivity(intent)
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)

        }
    }
    fun onclickSheduleDate(view: View) {
sheduleDate()
    }
private fun sheduleDate() {
    println("olur gibi")

    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        // Display Selected date in TextView
        val date = "" + dayOfMonth.toString() + ", " + month.toString() + ", " + year.toString()

        textinputSchedule.editText?.text =  date.toEditable()
    }, year, month, day)
    dpd.datePicker.minDate = System.currentTimeMillis()
    dpd.show()


}
    fun onclickTime(view: View) {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                val time = hourOfDay.toString()+":"+minute.toString()
                textinputTime.editText?.text = time.toEditable()
            }
        }, hour, minute, true)

mTimePicker.show()
    }
    private fun scheduleNotification(notification: Notification, delay: Int, flag : Int) {
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
        val mIntent = Intent(this, UiTodoEntryActivity::class.java)

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
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground))
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