package rpetrov.compass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}