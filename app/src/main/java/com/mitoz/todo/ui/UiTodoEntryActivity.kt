package com.mitoz.todo.ui

import android.app.*
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.android.material.textfield.TextInputLayout
import com.mitoz.todo.MainActivity
import com.mitoz.todo.R
import com.mitoz.todo.alarm.AlarmNotificationReciever
import com.mitoz.todo.database.DatabaseAppDatabase
import com.mitoz.todo.models.ModelsEntity
import kotlinx.android.synthetic.main.activity_ui_todo_entry.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


@Suppress("DEPRECATION")
class UiTodoEntryActivity : AppCompatActivity() {

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    private var imageUri: Uri? = null
    private val pickImage = 100
    lateinit var imageView: ImageView
    lateinit var textinputTitle: TextInputLayout
    private lateinit var db: DatabaseAppDatabase
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "1.1"
    private val description = "deneme"
    private var calendarMilis : Int = 0
    val c = Calendar.getInstance()
    private var dateStr : String = ""
    private var timeStr : String = ""
    private var todosList = ArrayList<ModelsEntity>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_todo_entry)
        imageView = findViewById(R.id.imageView)
        textinputTitle = findViewById(R.id.textinpuTitle)
        db = Room.databaseBuilder(this, DatabaseAppDatabase::class.java, "todo-list.db").build()
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        val bundle = intent.extras
        var s:Int? = null
        if (bundle != null) {
            s = bundle!!.getInt("modelid")
            GlobalScope.launch {
                todosList.add(db.todoDao().getItem(s))

            }
            val handler = Handler()
            handler.postDelayed({
                textinputTitle.editText?.setText(todosList[0].title.toEditable())
                textinputDescription.editText?.text = todosList[0].description.toEditable()
                textinputSchedule.editText?.text = todosList[0].date.toEditable()
                textinputTime.editText?.text = todosList[0].time.toEditable()
                imageView.setImageURI(Uri.parse(todosList[0].photograph))
                dateStr = todosList[0].date
                timeStr = todosList[0].time
                imageUri = Uri.parse(todosList[0].photograph)
            }, 200)
            buttonToolbarEntrytrash.setOnClickListener(){
              GlobalScope.launch {    db.todoDao().delete(todosList[0])}
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                System.runFinalization()
                Runtime.getRuntime().gc()
                System.gc()
                startActivity(intent)
            }
        } else {
            buttonToolbarEntrytrash.visibility = View.GONE
        }
        supportActionBar?.apply {
            title = "Olacak"
            elevation = 15F


        }


        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



buttonBrowseFiles.setOnClickListener(){
    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
    startActivityForResult(gallery, pickImage)
}


        buttonToolbarEntry.setOnClickListener {
            val inputTitle = textinpuTitle.editText?.text.toString()
            val inputDesction = textinputDescription.editText?.text.toString()


                println(calendarMilis.toString())
                if (inputTitle.isNullOrEmpty() or inputDesction.isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        "Görev ismi ve açıklama girmek zorunludur !",
                        Toast.LENGTH_LONG
                    ).show()
                    // alerts the user to fill in their number!
                } else {
                    if (calendarMilis > 0 and c.timeInMillis.toInt() != null) {
                        calendarMilis = c.timeInMillis.toInt() - System.currentTimeMillis().toInt()
                        getNotification("notify", "1.1.2")?.let { it1 ->
                            scheduleNotification(
                                it1,
                                calendarMilis,
                                calendarMilis
                            )
                        };
                    } else {
                        calendarMilis = 0
                    }
                    GlobalScope.launch {
                        if(s == null){
                            db.todoDao().insertAll(
                                ModelsEntity(
                                    0,
                                    inputTitle,
                                    inputDesction,
                                    calendarMilis,
                                    imageUri.toString(), 0, "not", dateStr, timeStr
                                )
                            )
                        }else {
                            todosList[0].title = inputTitle
                            todosList[0].description = inputDesction
                            todosList[0].scheduleFlag = calendarMilis
                            todosList[0].photograph = imageUri.toString()
                            todosList[0].date = dateStr
                            todosList[0].time = timeStr
                            db.todoDao().updateTodo(todosList[0])
                        }

                    }
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
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
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        val date = "" + dayOfMonth.toString() + "." + month.toString() + "." + year.toString()

        textinputSchedule.editText?.text =  date.toEditable()
        dateStr = date.toString()
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
                timeStr = time.toString()

            }
        }, hour, minute, false)

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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNotification(content: String, id : String): Notification? {
        val mIntent = Intent(this, UiTodoEntryActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName, R.layout.activity_after_notification)
        val soundUri =
            Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.alarm_sound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(id, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            notificationChannel.setSound(soundUri, audioAttributes)
            notificationManager.createNotificationChannel(notificationChannel)


            builder = Notification.Builder(this, id)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_foreground))
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +this.getPackageName()+"/"+R.raw.alarm_sound))



        } else {

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            notificationChannel.setSound(soundUri, audioAttributes)
            builder = Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_launcher_background))
                .setContentIntent(pendingIntent)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +this.getPackageName()+"/"+R.raw.alarm_sound))

        }
        return builder.build()
    }
}