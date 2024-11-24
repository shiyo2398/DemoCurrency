package com.shiyo.featurecurrency.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.shiyo.coreresources.model.NavigationAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    onSearchQuery: (query: String) -> Unit,
    onNavigate: (action: NavigationAction) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    fun clearSearchQuery() {
        searchQuery = ""
        onSearchQuery(searchQuery)
    }

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F7F9)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onNavigate.invoke(NavigationAction.NavigateBack)
                    clearSearchQuery()
                }) {
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
                        onSearchQuery.invoke(query)
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
                    clearSearchQuery()
                }) {
                    if (searchQuery.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
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
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview() {
    SearchTopBar(
        onSearchQuery = {}, // No-op for Preview
        onNavigate = {} // No-op for Preview
    )
}