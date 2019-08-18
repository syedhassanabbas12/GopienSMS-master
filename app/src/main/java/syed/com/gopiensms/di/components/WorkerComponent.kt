package syed.com.gopiensms.di.components

import dagger.Subcomponent
import syed.com.gopiensms.di.scopes.WorkerScope
import syed.com.gopiensms.workers.SendBulkSmsWorker


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/27/19}
 */


@WorkerScope
@Subcomponent
interface WorkerComponent {

    fun inject(sendBulkSmsWorker: SendBulkSmsWorker)
}