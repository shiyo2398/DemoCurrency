package com.shiyo.featurecurrency.data

import com.shiyo.coreresources.database.currency.CurrencyEntity
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem


fun CurrencyEntity.toItem() = CurrencyInfoItem(
    name = name,
    symbol = symbol,
    code = code
)

fun List<CurrencyEntity>.toItemList() = map(CurrencyEntity::toItem)