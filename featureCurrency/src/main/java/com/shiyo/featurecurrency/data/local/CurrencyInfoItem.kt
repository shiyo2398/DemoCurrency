package com.shiyo.featurecurrency.data.local

data class CurrencyInfoItem(
    val id : String,
    val name: String,
    val symbol: String,
    val code: String = "",
)
