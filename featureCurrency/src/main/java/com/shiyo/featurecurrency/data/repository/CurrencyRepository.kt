package com.shiyo.featurecurrency.data.repository

import com.shiyo.featurecurrency.data.local.CurrencyInfoItem

interface CurrencyRepository {
    suspend fun clearData()
    suspend fun insertData()
    suspend fun getCryptoCurrencyList(): List<CurrencyInfoItem>
    suspend fun getFiatCurrencyList(): List<CurrencyInfoItem>
    suspend fun getAllList(): List<CurrencyInfoItem>
}