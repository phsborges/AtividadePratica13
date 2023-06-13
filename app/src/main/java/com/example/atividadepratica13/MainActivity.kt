package com.example.atividadepratica13

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    private lateinit var editTextX: EditText
    private lateinit var editTextY: EditText
    private lateinit var editTextZ: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextX = findViewById(R.id.editTextX)
        editTextY = findViewById(R.id.editTextY)
        editTextZ = findViewById(R.id.editTextZ)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            editTextX.setText(x.toString())
            editTextY.setText(y.toString())
            editTextZ.setText(z.toString())

            if (Math.abs(x) > 7 || Math.abs(y) > 7 || Math.abs(z) > 7) {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("message", "Aceleração significativa detectada!")
                startActivity(intent)
            }
        }
    }
}
