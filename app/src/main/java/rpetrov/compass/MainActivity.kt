package rpetrov.compass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
 * Сенсоры (https://developer.android.com/develop/sensors-and-location/sensors/sensors_overview)
 * onResume / on Start, dialog
 * Анимация
 * Шеринг
 * Denug Tools
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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