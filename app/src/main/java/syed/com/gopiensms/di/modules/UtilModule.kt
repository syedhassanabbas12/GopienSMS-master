package syed.com.gopiensms.di.modules

import dagger.Module
import dagger.Provides
import syed.com.gopiensms.di.scopes.CustomApplicationScope
import syed.com.gopiensms.roomPersistence.BulkSmsDao
import syed.com.gopiensms.util.ViewModelFactory


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module(includes = [RoomDatabaseModule::class])
class UtilModule {

    @Provides
    @CustomApplicationScope
    fun viewModelFactory(bulkSmsDao: BulkSmsDao) = ViewModelFactory(bulkSmsDao)
}