package com.example.meghana_mokashi.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var mImgCompass : ImageView
    private var mSensorManager : SensorManager ?= null // ?= means nullable
    private var mSensor : Sensor ?= null
    private var mCurrentDegree = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mImgCompass = findViewById<ImageView>(R.id.compass)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ORIENTATION)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // !!. - means if it is non null
        val degree = Math.round(event!!.values[0])
        val ra = RotateAnimation(mCurrentDegree, -degree.toFloat(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        ra.duration = 120
        ra.fillAfter = true
        mImgCompass.startAnimation(ra)
        mCurrentDegree = -degree.toFloat()
    }
}
