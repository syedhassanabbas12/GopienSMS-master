package syed.com.gopiensms.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import syed.com.gopiensms.backend.MyCustomApplication
import syed.com.gopiensms.di.components.WorkerComponent


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/27/19}
 */

abstract class BaseWorker constructor(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private val workerComponent: WorkerComponent

    init {
        workerComponent = getApp().appComponent().workerComponent()
    }

    fun getWorkerComponent(): WorkerComponent {
        return workerComponent
    }

    private fun getApp(): MyCustomApplication {
        return applicationContext as MyCustomApplication
    }
}