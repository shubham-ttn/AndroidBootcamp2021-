package com.example.androidbootcamp2021

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.androidbootcamp2021.receivers.BatteryReceiver
import com.example.androidbootcamp2021.receivers.MyPhoneReceiver
import com.example.androidbootcamp2021.services.MyService
import com.example.androidbootcamp2021.thread.MyThread
import com.example.androidbootcamp2021.thread.MyThread1
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var batteryReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ques 1 : Use start() join() and sleep() methods in single program to execute different threads.
        // See thread directory
        threadDemo()

        //Ques 2 : Register receiver  for incoming calls and battery status.
        // See MyPhoneReceiver in Receivers directory used Static registration, see Manifest file
        // And BatteryReceiver in Receivers directory used dynamic registration
        // First, get permission from the user
        broadcastReceiverDemo()


        //Ques 3 : Music Player with raw file to play song in background.
        // See MyService in Services directory
        serviceDemo()

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(batteryReceiver)
    }

    private fun broadcastReceiverDemo() {
        receiverDemoBtn.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),1);
            }

            // Dynamic registration of Broadcast receiver
            batteryReceiver = BatteryReceiver()
            registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        }
    }

    private fun serviceDemo() {
        val serviceIntent = Intent(applicationContext, MyService::class.java)
        serviceStartDemoBtn.setOnClickListener {
            startService(serviceIntent)
        }
        serviceStopDemoBtn.setOnClickListener {
            stopService(serviceIntent)
        }
    }

    private fun threadDemo() {
        val myThread = MyThread(this)
        val myThread1 = MyThread1(this)

        myThread.start()

        // Using join() we are telling
        // that this thread must execute its
        // task first
        myThread1.start()
        myThread.join()
    }
}