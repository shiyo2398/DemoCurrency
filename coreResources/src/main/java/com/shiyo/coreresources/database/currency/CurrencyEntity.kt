package com.shiyo.coreresources.database.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val code: String = ""
)
