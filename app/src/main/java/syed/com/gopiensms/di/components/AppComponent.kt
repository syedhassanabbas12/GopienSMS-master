package syed.com.gopiensms.di.components

import com.squareup.picasso.Picasso
import dagger.Component
import syed.com.gopiensms.di.modules.HelperModule
import syed.com.gopiensms.di.modules.PicassoModule
import syed.com.gopiensms.di.modules.SharedPreferenceModule
import syed.com.gopiensms.di.modules.UtilModule
import syed.com.gopiensms.di.scopes.CustomApplicationScope
import syed.com.gopiensms.helper.NotificationHelper
import syed.com.gopiensms.helper.SharedPreferenceHelper
import syed.com.gopiensms.helper.UiHelper
import syed.com.gopiensms.roomPersistence.BulkSmsDao
import syed.com.gopiensms.util.ViewModelFactory


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@CustomApplicationScope
@Component(modules = [PicassoModule::class, HelperModule::class, UtilModule::class, SharedPreferenceModule::class])
interface AppComponent {

    fun getPicasso(): Picasso

    fun uiHelper(): UiHelper

    fun viewModelFactory(): ViewModelFactory

    fun notificationHelper(): NotificationHelper

    fun bulkSmsDao(): BulkSmsDao

    fun sharedPreferenceHelper(): SharedPreferenceHelper

    fun activityComponent(): ActivityComponent

    fun fragmentComponent(): FragmentComponent

    fun workerComponent(): WorkerComponent
}