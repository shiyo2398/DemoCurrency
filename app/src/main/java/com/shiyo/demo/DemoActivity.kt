package com.shiyo.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shiyo.featurecurrency.data.local.CurrencyEvent
import com.shiyo.featurecurrency.ui.CurrencyListScreen
import com.shiyo.featurecurrency.ui.CurrencyListViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyApp()
        }
    }


    @Composable
    fun CurrencyApp() {

        val navController = rememberNavController()

        NavHost(navController, startDestination = "main") {
            composable("main") { MainScreen(navController) }
            composable("currencyList/{type}") { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type")
                CurrencyListScreen(navController = navController, type = type)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(navController: NavHostController) {
        val viewModel: CurrencyListViewModel = getViewModel()

        Scaffold(
            topBar = { TopAppBar(title = { Text("Demo Activity") }) }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { viewModel.handleIntent(CurrencyEvent.ClearData) }) {
                    Text("Clear Data")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.handleIntent(CurrencyEvent.InsertData) }) {
                    Text("Insert All Data")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    navController.navigate("currencyList/crypto")
                    viewModel.handleIntent(CurrencyEvent.LoadCryptoCurrencyList)
                }) {
                    Text("Currency List - Crypto")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    navController.navigate("currencyList/fiat")
                    viewModel.handleIntent(CurrencyEvent.LoadFiatCurrencyList)
                }) {
                    Text("Currency List - Fiat")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    navController.navigate("currencyList/all")
                    viewModel.handleIntent(CurrencyEvent.LoadAllCurrencyList)
                }) {
                    Text("Show All Currencies")
                }
            }
        }
    }
}

@Composable
fun DummyScreen() {
    Text(text = "Dummy")
}
