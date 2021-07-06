package com.example.stopwatchkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var seconds = 0
    var running = false
    var wasRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null){
            savedInstanceState.getInt("seconds")
            savedInstanceState.getBoolean("running")
            savedInstanceState.getBoolean("wasRunning")
        }

        runTimer()
    }

    fun onStart(view : View){
        running = true
    }

    fun onStop(view : View){
        running = false
    }

    fun onReset(view : View){
        running = false
        seconds = 0
    }

    override fun onPause(){
        super.onPause()
        wasRunning = running
        running = false

    }

    override fun onResume(){
        super.onResume()
        if(wasRunning){
            running = true
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }

    private fun runTimer() {
        val timeView : TextView = findViewById(R.id.textView)

        var handler = Handler()

        handler.post(object : Runnable{
            override fun run(){
                var hours = seconds / 3600
                var minutes = (seconds % 3600)  / 60
                var secs = seconds % 60

                var time : String = String.format(Locale.getDefault(), "%d:%02d:%02d", hours,minutes,secs)

                timeView.setText(time)

                if(running){
                    seconds++
                }

                handler.postDelayed(this, 1000)

            }
        })



    }
}