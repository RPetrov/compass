package rpetrov.compass

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

/**

- гит: ветки, pr, мержи  и так далее.
- Посмотрим, как можно упростить себе работу;
- инструменты Android Studio: дебаггер, code-инспектор, layout-инспектор;
- ресурсы приложения для разных устройств;

- верстку;
- жизненный цикл Activity;

 */
class MainActivity : AppCompatActivity() {

    private val zImageView by lazy {
        findViewById<ImageView>(R.id.z)
    }

    private val sensorManager by lazy {
        SensorManager(this)
    }

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            event ?: return
            event.values // z, x, y
            zImageView.rotation = event.values[0]
            xImageView.rotation = event.values[1]
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            Log.d("MainActivity", "accuracy = $accuracy")
        }
    }

    private lateinit var xImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        xImageView = findViewById(R.id.x)
        zImageView.setOnClickListener {
            shareText()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(listener)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unRegisterListener(listener)
    }

    fun shareText() {
        val intent = Intent().apply {
            action = Intent.ACTION_CALL
            type = "application/vnd.ms-excel"
        }
        startActivity(Intent.createChooser(intent, "TEXT"))
    }

}