package com.shiyo.coreresources.util

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shiyo.coreresources.model.CurrencyAssetResponse
import java.io.IOException

object JSONUtil {

    fun parseCurrencyResponseFromAsset(context: Context): CurrencyAssetResponse? {
        return try {
            val inputStream = context.assets.open("localCurrency.json")
            val json = inputStream.bufferedReader().use { it.readText() }
            val gson = Gson()
            val typeToken = object : TypeToken<CurrencyAssetResponse>() {}.type
            gson.fromJson(json, typeToken)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}