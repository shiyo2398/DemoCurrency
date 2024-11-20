package com.shiyo.coreresources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shiyo.coreresources.database.currency.CurrencyDao
import com.shiyo.coreresources.database.currency.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}