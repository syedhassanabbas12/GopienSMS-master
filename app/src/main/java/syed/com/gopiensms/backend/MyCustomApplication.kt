package syed.com.gopiensms.backend

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import syed.com.gopiensms.di.components.AppComponent
import syed.com.gopiensms.di.components.DaggerAppComponent
import syed.com.gopiensms.di.modules.ApplicationContextModule
import timber.log.Timber


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

class MyCustomApplication : MultiDexApplication() {

    private val appComponent by lazy {
        DaggerAppComponent.builder()
            .applicationContextModule(ApplicationContextModule(applicationContext))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun appComponent(): AppComponent = appComponent
}