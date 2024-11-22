package com.shiyo.featurecurrency.data.local

import com.shiyo.coreresources.model.CurrencyType

sealed class CurrencyEvent{
    data object LoadAllCurrencyList : CurrencyEvent()
    data object InsertData : CurrencyEvent()
    data object ClearData : CurrencyEvent()
}
