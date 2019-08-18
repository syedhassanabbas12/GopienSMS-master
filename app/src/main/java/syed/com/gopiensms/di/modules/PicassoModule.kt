package syed.com.gopiensms.di.modules

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import syed.com.gopiensms.di.qualifiers.ApplicationContextQualifier
import syed.com.gopiensms.di.scopes.CustomApplicationScope


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */

@Module(includes = [NetworkModule::class])
class PicassoModule {

    @Provides
    @CustomApplicationScope
    fun getOkHttp3Downloader(okHttpClient: OkHttpClient) = OkHttp3Downloader(okHttpClient)

    @Provides
    @CustomApplicationScope
    fun getPicasso(@ApplicationContextQualifier context: Context, okHttpDownloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context)
            .downloader(okHttpDownloader)
            .build()
    }
}