package com.shiyo.featurecurrency.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shiyo.featurecurrency.data.local.CurrencyEvent
import com.shiyo.featurecurrency.data.local.CurrencyInfoItem
import com.shiyo.featurecurrency.data.local.CurrencyViewState
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(navController: NavController, type: String?) {
    val viewModel: CurrencyListViewModel = getViewModel()
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(type) {
        when (type) {
            "crypto" -> viewModel.handleIntent(CurrencyEvent.LoadCryptoCurrencyList)
            "fiat" -> viewModel.handleIntent(CurrencyEvent.LoadFiatCurrencyList)
            "all" -> viewModel.handleIntent(CurrencyEvent.LoadAllCurrencyList)
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF5F7F9)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.Gray
                                )
                            }
                            BasicTextField(
                                value = searchQuery,
                                onValueChange = { query ->
                                    searchQuery = query
                                    viewModel.searchCurrencies(query)
                                },
                                modifier = Modifier
                                    .weight(1f),
                                decorationBox = { innerTextField ->
                                    if (searchQuery.isEmpty()) {
                                        Text(
                                            "Search",
                                            style = TextStyle(color = Color.Gray, fontSize = 14.sp)
                                        )
                                    }
                                    innerTextField()
                                }
                            )
                            IconButton(onClick = {
                                searchQuery = ""
                                viewModel.searchCurrencies(searchQuery)
                            }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = Color.Gray
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF5F7F9),
                        titleContentColor = Color.Black,
                        navigationIconContentColor = Color.Gray,
                        actionIconContentColor = Color.Gray
                    )
                )
                HorizontalDivider(color = Color.Gray.copy(alpha = 0.15f), thickness = 1.dp)
            }
        }
    ) { innerPadding ->
        when (state) {
            is CurrencyViewState.Loading ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            is CurrencyViewState.Error ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 10.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        "Error: ${(state as CurrencyViewState.Error).message}",
                        modifier = Modifier.padding(innerPadding),
                        color = Color.Red
                    )
                }

            is CurrencyViewState.Empty -> EmptyState(innerPadding)

            is CurrencyViewState.Success -> CurrencyList(
                type = type,
                innerPadding = innerPadding,
                currencies = (state as CurrencyViewState.Success).currencies
            )
        }
    }
}

@Composable
fun CurrencyList(type: String?, innerPadding: PaddingValues, currencies: List<CurrencyInfoItem>) {
    LazyColumn(
        modifier = Modifier.padding(innerPadding),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(currencies) { currency ->
            CurrencyListItem(currency, type)
        }
    }
}

@Composable
fun CurrencyListItem(currency: CurrencyInfoItem, type: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currency.name.take(1),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = currency.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        if (currency.code.isEmpty()) {
            Text(
                text = currency.symbol,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(end = 10.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun EmptyState(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "No Results",
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No Results",
            color = Color.Gray,
        )
    }
}
