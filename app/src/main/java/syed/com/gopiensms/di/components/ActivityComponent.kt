package syed.com.gopiensms.di.components

import dagger.Subcomponent
import syed.com.gopiensms.activities.main.ui.MainActivity
import syed.com.gopiensms.activities.sendBulkSms.ui.SendBulkSmsActivity
import syed.com.gopiensms.di.scopes.ActivityScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@ActivityScope
@Subcomponent
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(mainActivity: SendBulkSmsActivity)
}