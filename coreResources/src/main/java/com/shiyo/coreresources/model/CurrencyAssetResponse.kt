package com.shiyo.coreresources.model

// Data classes for parsing JSON
data class CurrencyAssetResponse(
    val crypto: List<Currency>,
    val fiat: List<Currency>
)

data class Currency(
    val id: String,
    val name: String,
    val symbol: String,
    val code: String
)
