package com.atnzvdev.thousand.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(onNavigationBack: () -> Unit) {
    Scaffold(
        Modifier.padding(50.dp)
    ) {
        Text("Hello world")
    }
}