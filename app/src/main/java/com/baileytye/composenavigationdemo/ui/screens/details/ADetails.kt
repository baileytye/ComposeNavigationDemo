package com.baileytye.composenavigationdemo.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ADetails(
    idFromConstructor: String,
    viewModel: ADetailsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val idFromViewModel by viewModel.idFromViewModel

    Scaffold(topBar = {
        IconButton(onClick = { navigateUp() }) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Screen A Details")
            Text(text = "Constructor ID: $idFromConstructor")
            Text(text = "ViewModel ID: $idFromViewModel")
        }
    }
}