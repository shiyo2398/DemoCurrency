package com.shiyo.demo

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shiyo.coreresources.model.CurrencyType
import com.shiyo.coreresources.model.NavigationAction
import com.shiyo.coreresources.model.Screen
import com.shiyo.featurecurrency.data.local.CurrencyEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isDataEmpty: Boolean,
    onAction: (event: CurrencyEvent) -> Unit,
    onNavigate: (action: NavigationAction) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = { TopAppBar(title = { Text("Demo Activity") }) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                onAction.invoke(CurrencyEvent.ClearData)
                Toast.makeText(context, "Clear successfully", Toast.LENGTH_SHORT).show()
            }, enabled = isDataEmpty.not()) {
                Text("Clear Data")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                onAction.invoke(CurrencyEvent.InsertData)
                Toast.makeText(context, "Inserted successfully", Toast.LENGTH_SHORT).show()
            }, enabled = isDataEmpty) {
                Text("Insert All Data")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                onNavigate.invoke(
                    NavigationAction.NavigateToScreen(
                        Screen.CurrencyList(
                            type = CurrencyType.CRYPTO
                        )
                    )
                )
            }) {
                Text("Currency List - Crypto")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                onNavigate.invoke(
                    NavigationAction.NavigateToScreen(
                        Screen.CurrencyList(
                            type = CurrencyType.FIAT
                        )
                    )
                )
            }) {
                Text("Currency List - Fiat")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                onNavigate.invoke(
                    NavigationAction.NavigateToScreen(
                        Screen.CurrencyList(
                            type = CurrencyType.ALL
                        )
                    )
                )
            }) {
                Text("Show All Currencies")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        isDataEmpty = true,
        onAction = {},
        onNavigate = {})
}