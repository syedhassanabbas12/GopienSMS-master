package syed.com.gopiensms.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import syed.com.gopiensms.di.qualifiers.ApplicationContextQualifier
import syed.com.gopiensms.di.scopes.CustomApplicationScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module
class ApplicationContextModule constructor(private val context: Context) {

    @Provides
    @CustomApplicationScope
    @ApplicationContextQualifier
    fun getContext() = context
}