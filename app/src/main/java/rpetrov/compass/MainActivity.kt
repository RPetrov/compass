package rpetrov.compass

import android.animation.ValueAnimator
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Вью. Image, Text, languages, tablet, divider
 * Сенсоры (https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview)
 * onResume / on Start, dialog
 * Анимация
 * Шеринг
 * Debug Tools
 */
class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager

    internal val xImageView by lazy {
        findViewById<ImageView>(R.id.x_image)
    }
    internal val yImageView by lazy {
        findViewById<ImageView>(R.id.y_image)
    }
    private lateinit var zImageView: ImageView

    private var lastTime = System.currentTimeMillis()

    private var z = 0F

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event ?: return
            if (System.currentTimeMillis() - lastTime < 3000)
                return
            Log.i(TAG, "event = ${event.values[0]}, ${event.values[1]}, ${event.values[2]}")

            with(ValueAnimator.ofFloat(zImageView.rotation, event.values[0])) {
                duration = 1000
                addUpdateListener {
                    zImageView.rotation = it.animatedValue as Float
                }
                start()
            }

            z = event.values[0]
            //zImageView.rotation = event.values[0]
            xImageView.rotation = event.values[1]
            yImageView.rotation = event.values[2]
            lastTime = System.currentTimeMillis()

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.i(TAG, "accuracy = $accuracy")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        zImageView = findViewById<ImageView>(R.id.z_image)
        zImageView.setOnClickListener {
            share()
        }

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager


//        if (sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
//            // Success! There's a magnetometer.
//        } else {
//            Toast.makeText(this, "No sensor!", Toast.LENGTH_LONG).show()
//        }
    }

    override fun onResume() {
        super.onResume()
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensor?.apply {
            //Toast.makeText(this@MainActivity, "OK!", Toast.LENGTH_LONG).show()
            sensorManager.registerListener(listener, this, SensorManager.SENSOR_DELAY_NORMAL)
        } ?: Toast.makeText(this, "No sensor!", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(listener)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun share() {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, z.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}