package com.shiyo.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shiyo.coreresources.model.CurrencyType
import com.shiyo.coreresources.model.Screen
import com.shiyo.coreresources.util.NavigatorUtil
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
        val navigatorUtil = NavigatorUtil(navController)

        val viewModel: CurrencyListViewModel = getViewModel()
        val state by viewModel.state.collectAsState()
        val isDatabaseEmptyState by viewModel.isDatabaseEmptyState.collectAsState()

        NavHost(navController, startDestination = Screen.Main().route) {
            composable(Screen.Main().route) {
                MainScreen(
                    isDataEmpty = isDatabaseEmptyState,
                    onAction = {
                        viewModel.handleIntent(it)
                    },
                    onNavigate = {
                        navigatorUtil.navigate(it)
                    })
            }
            composable(
                route = "${Screen.CurrencyList().route}/{type}",
                arguments = listOf(
                    navArgument("type") { type = NavType.StringType })
            ) { backStackEntry ->
                // Extract the enum type from the arguments
                val typeString = backStackEntry.arguments?.getString("type")
                val type = typeString?.let { CurrencyType.valueOf(it) }
                    ?: CurrencyType.ALL // Convert string back to enum

                CurrencyListScreen(
                    type = type,
                    onEvent = {
                        viewModel.changeCurrencyType(it)
                    },
                    state = state, onSearchQuery = {
                        viewModel.searchCurrencies(it)
                    }, onNavigate = {
                        navigatorUtil.navigate(it)
                    })
            }
        }
    }
}


