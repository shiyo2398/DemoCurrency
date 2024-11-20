package com.shiyo.demo

import com.shiyo.coreresources.base.BaseApplication
import com.shiyo.coreresources.database.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyApplication)
            modules(databaseModule)
        }
    }
}