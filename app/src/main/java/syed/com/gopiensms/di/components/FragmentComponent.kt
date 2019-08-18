package syed.com.gopiensms.di.components

import dagger.Subcomponent
import syed.com.gopiensms.activities.main.fragments.SplashScreenFragment
import syed.com.gopiensms.activities.main.fragments.mainScreen.tabFragments.history.ui.HistoryFragment
import syed.com.gopiensms.activities.main.fragments.mainScreen.tabFragments.settings.ui.SettingsFragment
import syed.com.gopiensms.di.scopes.FragmentScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@FragmentScope
@Subcomponent
interface FragmentComponent {

    fun inject(splashScreenFragment: SplashScreenFragment)

    fun inject(splashScreenFragment: HistoryFragment)

    fun inject(settingsFragment: SettingsFragment)
}