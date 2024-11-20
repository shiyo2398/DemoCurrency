package com.shiyo.featurecurrency.data.local

sealed class CurrencyEvent{
    data object LoadCryptoCurrencyList : CurrencyEvent()
    data object LoadFiatCurrencyList : CurrencyEvent()
    data object LoadAllCurrencyList : CurrencyEvent()
    data object InsertData : CurrencyEvent()
    data object ClearData : CurrencyEvent()
}
