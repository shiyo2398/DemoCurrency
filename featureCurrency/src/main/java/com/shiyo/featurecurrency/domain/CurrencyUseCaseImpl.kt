package com.shiyo.featurecurrency.domain

import android.content.Context
import com.shiyo.coreresources.database.currency.CurrencyDao
import com.shiyo.coreresources.database.currency.CurrencyEntity
import com.shiyo.coreresources.util.JSONUtil
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem
import com.shiyo.featurecurrency.data.repository.CurrencyRepository
import com.shiyo.featurecurrency.data.toItemList
import com.shiyo.featurecurrency.data.toLocalEntityList

class CurrencyUseCaseImpl(
    private val context: Context,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {

    private val currencyAssetResponse by lazy {
        JSONUtil.parseCurrencyResponseFromAsset(context)
    }

    private val cryptoCurrencyList: List<CurrencyEntity> by lazy {
        currencyAssetResponse?.crypto?.toLocalEntityList() ?: emptyList()
    }

    private val fiatCurrencyList: List<CurrencyEntity> by lazy {
        currencyAssetResponse?.fiat?.toLocalEntityList() ?: emptyList()
    }

    override suspend fun clearData() {
        currencyDao.clearData()
    }

    override suspend fun insertData() {
        currencyDao.insertCurrencies(cryptoCurrencyList + fiatCurrencyList)
    }

    override suspend fun getAllList(): List<CurrencyInfoItem> {
        return currencyDao.getAllCurrencies().toItemList()
    }
}