package dt.prod.patternvm

import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApp : MultiDexApplication() {
    companion object {
        lateinit var instance: BaseApp
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Kotpref.init(this)
        sharedPreferences = getSharedPreferences("default", MODE_PRIVATE)
    }
}