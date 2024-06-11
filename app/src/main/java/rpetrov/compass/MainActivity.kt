package rpetrov.compass

import android.animation.ValueAnimator
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
import android.widget.TextView

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
 * onResume / onStart
 */
class MainActivity : AppCompatActivity() {

    val zView by lazy {
        findViewById<ImageView>(R.id.z)
    }

    val xView by lazy {
        findViewById<ImageView>(R.id.x)
    }

    val yView by lazy {
        findViewById<ImageView>(R.id.y)
    }


    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var sensorEventListener: SensorEventListener? = null
    private var lastTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorEventListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (System.currentTimeMillis() < lastTime + 1000)
                    return

                event ?: return
                val zorientation = event.values?.get(0) ?: 0F // Z
                val xorientation = event.values?.get(1) ?: 0F // X
                val yorientation = event.values?.get(2) ?: 0F // Y

                ValueAnimator.ofFloat(zView.rotation, zorientation).also {
                    it.interpolator = LinearInterpolator() // линейный интерполятор
                    it.duration = 1000
                    it.addUpdateListener {
                        zView.rotation = it.animatedValue as Float
                    }
                    it.start()
                }

                ValueAnimator.ofFloat(xView.rotation, xorientation).also {
                    it.interpolator = DecelerateInterpolator() // замедля́ющаяся анимация
                    it.duration = 1000
                    it.addUpdateListener {
                        xView.rotation = it.animatedValue as Float
                    }
                    it.start()
                }


                ValueAnimator.ofFloat(yView.rotation, yorientation).also {
                    it.interpolator = AccelerateInterpolator() // ускоряющаяся анимация
                    it.duration = 1000
                    it.addUpdateListener {
                        yView.rotation = it.animatedValue as Float
                    }
                    it.start()
                }

                lastTime = System.currentTimeMillis()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.i("MainActivity", "onAccuracyChanged. accuracy = ${accuracy}")
            }
        }
        sensorManager.registerListener(
            sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL
        )


    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorEventListener)
    }
}