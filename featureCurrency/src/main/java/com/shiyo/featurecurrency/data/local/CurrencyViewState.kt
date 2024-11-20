package com.shiyo.featurecurrency.data.local

sealed class CurrencyViewState {
    data object Empty : CurrencyViewState()
    data object Loading : CurrencyViewState()
    data class Success(val currencies: List<CurrencyInfoItem>) : CurrencyViewState()
    data class Error(val message: String) : CurrencyViewState()
}