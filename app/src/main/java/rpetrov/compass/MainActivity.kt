package rpetrov.compass

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import java.lang.ref.WeakReference

/**
 * 1.
 * - Создание веток в гит, ветки для спринта, разные пути, потом все мержим в dev, потом в main
 * - Отдельная ветка чтобы “показать” наставнику проблему
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

    val xView by lazy {
        findViewById<ImageView>(R.id.x)
    }

    val yView by lazy {
        findViewById<ImageView>(R.id.y)
    }

    val zView by lazy {
        findViewById<ImageView>(R.id.z)
    }


    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event ?: return
            val zRotation = event.values[0]
            val xRotation = event.values[1]
            val yRotation = event.values[2]
            xView.rotation = xRotation
            yView.rotation = yRotation
            zView.rotation = zRotation
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    private val sensorManager by lazy { SensorManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        xView.setOnClickListener {
            shareText()
        }

        ActivityManager.list.add(this)

        // val time = System.currentTimeMillis()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        sensorManager.registerListener(sensorEventListener)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()
        // sensorManager.unRegisterListener(sensorEventListener)

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun shareText() {
        val intent = Intent().apply {
            action = Intent.ACTION_CALL
            type = "application/vnd.ms-excel"

        }
        startActivity(Intent.createChooser(intent, "TEXT"))
    }
}
