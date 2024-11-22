package com.shiyo.coreresources.model


sealed class Screen(open val route: String) {

    data class Main(override val route: String = "main") : Screen(route)

    data class CurrencyList(
        override val route: String = "currencyList",
        val type: CurrencyType = CurrencyType.ALL
    ) : Screen(route)
}