package syed.com.gopiensms.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import syed.com.gopiensms.activities.main.fragments.mainScreen.tabFragments.history.viewModel.HistoryFragmentViewModel
import syed.com.gopiensms.activities.main.fragments.mainScreen.tabFragments.settings.viewModel.SettingsFragmentViewModel
import syed.com.gopiensms.roomPersistence.BulkSmsDao

/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val bulkSmsDao: BulkSmsDao) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryFragmentViewModel::class.java) -> HistoryFragmentViewModel(bulkSmsDao) as T
            modelClass.isAssignableFrom(SettingsFragmentViewModel::class.java) -> SettingsFragmentViewModel(bulkSmsDao) as T
            else -> throw IllegalArgumentException("Unknown view model class")
        }
    }
}