package rpetrov.compass

import android.animation.ValueAnimator
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.ImageView

/**
 *
 * гит: ветки, pr, мержи м так далее. Посмотрим, как можно упростить себе работу.
 * Инструменты Android Studio:
 * дебаггер,
 * code-инспектор,
 * layout-инспектор.
 *
 *
 * Вью. Image, Text, languages, tablet
 * Сенсоры (https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview)
 *
 * Анимация
 * Шеринг (onResume / on Start, dialog)
 */
class MainActivity : AppCompatActivity() {

    private val imageViewX by lazy {
        findViewById<ImageView>(R.id.x)
    }
    private val imageViewY by lazy {
        findViewById<ImageView>(R.id.y)
    }
    private val imageViewZ by lazy {
        findViewById<ImageView>(R.id.z)
    }


    private var lastTime: Long = 0

    private var sensorManager: SensorManager? = null

    private var listener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            val duration = 1000L
            val values = event?.values ?: return
            val z = values[0]
            val x = values[1]
            val y = values[2]
            if (System.currentTimeMillis() > lastTime + duration) {
                Log.i("MainActivity", "onSensorChanged values = ${event?.values?.joinToString()}")
                lastTime = System.currentTimeMillis()

                val animatorZ = ValueAnimator.ofFloat(imageViewZ.rotation, z)
                animatorZ.duration = duration
                animatorZ.repeatCount = 0
                animatorZ.addUpdateListener {
                    imageViewZ.rotation = it.animatedValue as Float
                }
                animatorZ.interpolator = LinearInterpolator()
                animatorZ.start()

                val animatorX = ValueAnimator.ofFloat(imageViewX.rotation, x)
                animatorX.duration = duration
                animatorX.repeatCount = 0
                animatorX.addUpdateListener {
                    imageViewX.rotation = it.animatedValue as Float
                }
                animatorX.interpolator = AccelerateInterpolator()
                animatorX.start()

                val animatorY = ValueAnimator.ofFloat(imageViewY.rotation, y)
                animatorY.duration = duration
                animatorY.repeatCount = 0
                animatorY.addUpdateListener {
                    imageViewY.rotation = it.animatedValue as Float
                }
                animatorY.interpolator = DecelerateInterpolator()
                animatorY.start()

            }

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.i("MainActivity", "onAccuracyChanged accuracy = $accuracy")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        imageViewZ.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Hello!!!")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, "TITLE"))
        }

    }

    override fun onResume() {
        super.onResume()
        val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorManager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(listener)
    }


}