package com.example.currencyconverter.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EnterNameScreen(
    modifier: Modifier = Modifier,
    onContinue: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter your name:", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = name, onValueChange = { name = it })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onContinue(name) }, enabled = name.isNotBlank()) {
            Text("Continue")
        }
    }
}