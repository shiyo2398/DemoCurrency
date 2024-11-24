package com.shiyo.featurecurrency.data

import com.shiyo.coreresources.database.currency.CurrencyEntity
import com.shiyo.coreresources.model.Currency
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem


//from local database to item
fun CurrencyEntity.toItem() = CurrencyInfoItem(
    id = id,
    name = name,
    symbol = symbol,
    code = code
)

fun List<CurrencyEntity>.toItemList() = map(CurrencyEntity::toItem)


//from Asset to local Database entity
fun Currency.toLocalEntity() =  CurrencyEntity(
    id = id,
    name = name,
    symbol = symbol,
    code = code.orEmpty()
)

fun List<Currency>.toLocalEntityList() = map(Currency::toLocalEntity)