package syed.com.gopiensms.util

import okhttp3.logging.HttpLoggingInterceptor
import syed.com.gopiensms.BuildConfig


/**
 * Ahsen Saeed}
 * ahsansaeed067@gmail.com}
 * 6/25/19}
 */


val debugLevel = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor.Level.BODY
} else {
    HttpLoggingInterceptor.Level.NONE
}