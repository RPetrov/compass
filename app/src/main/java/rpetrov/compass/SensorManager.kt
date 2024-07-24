package rpetrov.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity.SENSOR_SERVICE


internal class SensorManager(val context: Context) {

    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)

    fun registerListener(listener: SensorEventListener) {

        sensorManager.registerListener(
            listener, sensor, SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    fun un(listener: SensorEventListener) {
        sensorManager.unregisterListener(listener)
    }
}