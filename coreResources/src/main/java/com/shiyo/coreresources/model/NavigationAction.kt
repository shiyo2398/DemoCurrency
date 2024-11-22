package com.shiyo.coreresources.model

sealed class NavigationAction {
    data object NavigateBack : NavigationAction()
    data class NavigateToScreen(val route: Screen) : NavigationAction()
}