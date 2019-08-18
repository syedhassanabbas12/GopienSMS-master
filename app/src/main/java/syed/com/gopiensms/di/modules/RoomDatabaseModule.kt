package syed.com.gopiensms.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import syed.com.gopiensms.di.qualifiers.ApplicationContextQualifier
import syed.com.gopiensms.di.scopes.CustomApplicationScope
import syed.com.gopiensms.roomPersistence.BulkSmsDatabase


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/28/19}
 */
@Module(includes = [ApplicationContextModule::class])
class RoomDatabaseModule {

    @Provides
    @CustomApplicationScope
    fun bulkSmsDatabase(@ApplicationContextQualifier context: Context) = BulkSmsDatabase.getInstance(context)

    @Provides
    @CustomApplicationScope
    fun bulkSmsDao(bulkSmsDatabase: BulkSmsDatabase) = bulkSmsDatabase.bulkSmsDao()
}