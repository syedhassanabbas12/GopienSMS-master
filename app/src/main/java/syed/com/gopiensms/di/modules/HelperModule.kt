package syed.com.gopiensms.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import syed.com.gopiensms.di.qualifiers.ApplicationContextQualifier
import syed.com.gopiensms.di.scopes.CustomApplicationScope
import syed.com.gopiensms.helper.NotificationHelper
import syed.com.gopiensms.helper.UiHelper


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module(includes = [ApplicationContextModule::class])
class HelperModule {

    @Provides
    @CustomApplicationScope
    fun uiHelper(@ApplicationContextQualifier context: Context) = UiHelper(context)

    @Provides
    @CustomApplicationScope
    fun notificationHelper(@ApplicationContextQualifier context: Context) = NotificationHelper(context)
}