package com.shiyo.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyApp()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CurrencyApp() {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val navController = rememberNavController()
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerContent()
            },
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Currencies") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController,
                    startDestination = "currencyList",
                    modifier = Modifier.padding(it)
                ) {
                    composable("currencyList") {
                        DummyScreen()
                    }
                }
            }
        }
    }

    @Composable
    fun DrawerContent() {
        Column(modifier = Modifier.systemBarsPadding()) {
            Button(onClick = {

            }) {
                Text("Clear Data")
            }
            Button(onClick = {}) {
                Text("Insert Data")
            }
            Button(onClick = { }) {
                Text("Use Crypto List")
            }
            Button(onClick = { }) {
                Text("Use Fiat List")
            }
            Button(onClick = { }) {
                Text("Show Purchasable")
            }
        }
    }
}

@Composable
fun DummyScreen() {
    Text(text = "Dummy")
}
