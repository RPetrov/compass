package rpetrov.compass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**

- гит: ветки, pr, мержи  и так далее.
 - Посмотрим, как можно упростить себе работу;
- инструменты Android Studio: дебаггер, code-инспектор, layout-инспектор;
- ресурсы приложения для разных устройств;
- верстку;
- жизненный цикл Activity;

 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun shareText() {
        val intent = Intent().apply {
            action = Intent.ACTION_CALL
            type = "application/vnd.ms-excel"
        }
        startActivity(Intent.createChooser(intent, "TEXT"))
    }

}