package rpetrov.compass

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * 1.
 * - Создание веток в гит
 *   dev, main, checkout, marge, push, reset
 * - Создание PR
 * - Краши: стектрейсы, дебаг.
 * - Лейаут-инспектор - пример того, как работает, как решает проблему.
 * - Анализатор кода
 *
 *
 * 2.
 * Вью. Image, Text, languages, tablet
 * Сенсоры (https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview)
 * onResume / on Start, dialog


 */
class MainActivity : AppCompatActivity() {
    private lateinit var manager: SensorManager

    val viewZ by lazy {
        findViewById<ImageView>(R.id.z)
    }

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event ?: return
            val zOrientation = event.values[0] // in degrees
            viewZ.rotation = zOrientation
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("MainActivity", "onAccuracyChanged $accuracy")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = SensorManager(this)

        viewZ.setOnClickListener {
            shareText()
        }

    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(listener)
    }

    override fun onPause() {
        super.onPause()
        manager.unRegisterListener(listener)
    }

    fun shareText() {
        val intent = Intent().apply {
            action = Intent.ACTION_CALL
            type = "application/vnd.ms-excel"
        }
        startActivity(Intent.createChooser(intent, "TEXT"))
    }

}