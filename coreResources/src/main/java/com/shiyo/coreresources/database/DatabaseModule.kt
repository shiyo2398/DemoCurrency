package com.shiyo.coreresources.database

import androidx.room.Room
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), CurrencyDatabase::class.java, "currency-db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<CurrencyDatabase>().currencyDao() }
}