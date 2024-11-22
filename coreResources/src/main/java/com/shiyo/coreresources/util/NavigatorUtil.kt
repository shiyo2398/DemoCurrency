package com.shiyo.coreresources.util

import androidx.navigation.NavController
import com.shiyo.coreresources.model.NavigationAction
import com.shiyo.coreresources.model.Screen

class NavigatorUtil(private val navController: NavController) {

    fun navigate(action: NavigationAction) {
        when (action) {
            is NavigationAction.NavigateBack -> {
                navController.popBackStack()
            }

            is NavigationAction.NavigateToScreen -> {
                navigateToScreen(action.route)
            }
        }
    }

    private fun navigateToScreen(screen: Screen) {
        when (screen) {
            is Screen.CurrencyList -> {
                navController.navigate("${Screen.CurrencyList().route}/${screen.type.name}")
            }
            else -> {
                navController.navigate(screen.route)
            }
        }
    }
}