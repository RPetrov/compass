package rpetrov.compass

import android.animation.ValueAnimator
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

/**
 * Создание веток в гит, Часть 1
 *
 * git merge
 *
 *
 * Краши: стектрейсы, дебаг.
 * Лейаут-инспектор пример того, как работает, как решает проблему.
 * Анализатор кода
 *
 * Часть 2
 * Views, layout
 * orientation
 * Сенсоры (https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview)
 * onResume / on Start, dialog
 * Анимация
 * Шеринг
 *
 *
 * Debug Tools
 */
class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private val xImage by lazy {
        findViewById<ImageView>(R.id.x)
    }
    private val yImage by lazy {
        findViewById<ImageView>(R.id.y)
    }
    private val zImage by lazy {
        findViewById<ImageView>(R.id.z)
    }

    private var lastUpdateTIme: Long = 0

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (System.currentTimeMillis() - lastUpdateTIme < 1000)
                return

            event ?: return
            Log.i("COMPASS_TAG", "onSensorChanged ${hashCode()}")

            val animatorZ = ValueAnimator.ofFloat(zImage.rotation, event.values[0])
            animatorZ.duration = 1000
            animatorZ.interpolator = DecelerateInterpolator()
            animatorZ.addUpdateListener {
                zImage.rotation = it.animatedValue as Float
            }
            animatorZ.start()

            val animatorX = ValueAnimator.ofFloat(xImage.rotation, event.values[1])
            animatorX.duration = 1000
            animatorX.addUpdateListener {
                xImage.rotation = it.animatedValue as Float
            }
            animatorX.start()

            yImage.rotation = event.values[2]

            lastUpdateTIme = System.currentTimeMillis()
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.i("COMPASS_TAG", "onAccuracyChanged accuracy = ${accuracy}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  textView.text = "!!!"

        xImage.setOnClickListener {
            share()
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    }

    override fun onStart() {
        super.onStart()
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(listener)
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hello!")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}