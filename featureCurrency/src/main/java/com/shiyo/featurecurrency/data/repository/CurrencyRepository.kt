package com.shiyo.featurecurrency.data.repository

import com.shiyo.featurecurrency.data.local.CurrencyInfoItem

interface CurrencyRepository {
    suspend fun clearData()
    suspend fun insertData()
    suspend fun getAllList(): List<CurrencyInfoItem>
}