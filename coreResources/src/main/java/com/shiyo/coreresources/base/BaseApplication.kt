package com.shiyo.coreresources.base

import android.app.Application
import com.shiyo.coreresources.BuildConfig
import timber.log.Timber

open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}