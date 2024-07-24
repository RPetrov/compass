package rpetrov.compass

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

/**
 *
 * - Создание веток в гит, ветки для спринта, разные пути, потом все мержим в dev, потом в main
 * - Отдельная ветка чтобы “показать” наставнику проблему
 * - Создание PR
 * - Краши: стектрейсы, дебаг.
 * - Лейаут-инспектор - пример того, как работает, как решает проблему.
 * - Анализатор кода
 *
 * Вью. Image, Text, languages, tablet
 * onResume / on Start, dialog
 * Denug Tools
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // verbose, debug, info, warn, error, fatal error
        // ->
        Log.w("MY_TAF", "SOME LOG")
        Log.w("MY_TAF", "SOME LOG 2")



    }


    fun shareText(text: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, "Current orientation"))
    }
}