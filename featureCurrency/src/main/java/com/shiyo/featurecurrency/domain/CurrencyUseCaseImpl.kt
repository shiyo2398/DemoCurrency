package com.shiyo.featurecurrency.domain

import com.shiyo.coreresources.database.currency.CurrencyDao
import com.shiyo.coreresources.database.currency.CurrencyEntity
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem
import com.shiyo.featurecurrency.data.repository.CurrencyRepository
import com.shiyo.featurecurrency.data.toItemList

class CurrencyUseCaseImpl(private val currencyDao: CurrencyDao) : CurrencyRepository {

    private val cryptoCurrencyList = listOf(
        CurrencyEntity(id = "BTC", name = "Bitcoin", symbol = "BTC"),
        CurrencyEntity(id = "ETH", name = "Ethereum", symbol = "ETH"),
        CurrencyEntity(id = "XRP", name = "XRP", symbol = "XRP"),
        CurrencyEntity(id = "BCH", name = "Bitcoin Cash", symbol = "BCH"),
        CurrencyEntity(id = "LTC", name = "Litecoin", symbol = "LTC"),
        CurrencyEntity(id = "EOS", name = "EOS", symbol = "EOS"),
        CurrencyEntity(id = "BNB", name = "Binance Coin", symbol = "BNB"),
        CurrencyEntity(id = "LINK", name = "Chainlink", symbol = "LINK"),
        CurrencyEntity(id = "NEO", name = "NEO", symbol = "NEO"),
        CurrencyEntity(id = "ETC", name = "Ethereum Classic", symbol = "ETC"),
        CurrencyEntity(id = "ONT", name = "Ontology", symbol = "ONT"),
        CurrencyEntity(id = "CRO", name = "Crypto.com Chain", symbol = "CRO"),
        CurrencyEntity(id = "CUC", name = "Cucumber", symbol = "CUC"),
        CurrencyEntity(id = "USDC", name = "USD Coin", symbol = "USDC")
    )

    private val fiatCurrencyList = listOf(
        CurrencyEntity(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD"),
        CurrencyEntity(id = "EUR", name = "Euro", symbol = "€", code = "EUR"),
        CurrencyEntity(id = "GBP", name = "British Pound", symbol = "£", code = "GBP"),
        CurrencyEntity(id = "HKD", name = "Hong Kong Dollar", symbol = "$", code = "HKD"),
        CurrencyEntity(id = "JPY", name = "Japanese Yen", symbol = "¥", code = "JPY"),
        CurrencyEntity(id = "AUD", name = "Australian Dollar", symbol = "$", code = "AUD"),
        CurrencyEntity(id = "USD", name = "United States Dollar", symbol = "$", code = "USD")
    )

    override suspend fun clearData() {
        currencyDao.clearData()
    }

    override suspend fun insertData() {
        currencyDao.clearData()
        currencyDao.insertCurrencies(cryptoCurrencyList + fiatCurrencyList)
    }

    override suspend fun getCryptoCurrencyList(): List<CurrencyInfoItem> {
        return currencyDao.getCryptoCurrencies().toItemList()
    }

    override suspend fun getFiatCurrencyList(): List<CurrencyInfoItem> {
        return currencyDao.getFiatCurrencies().toItemList()
    }

    override suspend fun getAllList(): List<CurrencyInfoItem> {
        return currencyDao.getAllCurrencies().toItemList()
    }
}