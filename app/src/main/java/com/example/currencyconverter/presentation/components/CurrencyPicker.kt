package com.example.currencyconverter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPicker(
    label: String = "",
    selectedCurrency: String,
    currencies: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    var showSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredCurrencies = currencies.filter {
        it.contains(searchQuery, ignoreCase = true)
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(showSheet) {
        if (showSheet) {
            val index = filteredCurrencies.indexOf(selectedCurrency)
            if (index != -1) {
                coroutineScope.launch {
                    listState.scrollToItem(index)
                    listState.animateScrollToItem(index)
                }
            }
        }
    }

    Box {
        Button(onClick = { showSheet = true }) {
            Text("$label$selectedCurrency")
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search currency...") },
                    modifier = Modifier.fillMaxWidth()
                )

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxHeight(0.6f)
                ) {
                    itemsIndexed(filteredCurrencies) { _, currency ->
                        val isSelected = currency == selectedCurrency
                        Text(
                            text = currency,
                            color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (isSelected) ButtonDefaults.buttonColors().containerColor else Color.Transparent)
                                .clickable {
                                    onCurrencySelected(currency)
                                    showSheet = false
                                    searchQuery = ""
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
