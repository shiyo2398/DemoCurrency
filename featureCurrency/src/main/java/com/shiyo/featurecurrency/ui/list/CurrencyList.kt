package com.shiyo.featurecurrency.ui.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem

@Composable
fun CurrencyList(
    currencies: List<CurrencyInfoItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(
            items = currencies,
            key = { it.id } // Use unique keys
        ) { currency ->
            CurrencyListContent(currency)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyListPreview() {
    CurrencyList(
        currencies = listOf(
            CurrencyInfoItem(id = "BTC", name = "Bitcoin", symbol = "BTC"),
            CurrencyInfoItem(id = "ETH", name = "Ethereum", symbol = "ETH"),
            CurrencyInfoItem(id = "LTC", name = "Litecoin", symbol = "LTC")
        )
    )
}