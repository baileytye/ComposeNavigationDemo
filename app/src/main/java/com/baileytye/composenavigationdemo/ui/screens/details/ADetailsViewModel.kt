package com.baileytye.composenavigationdemo.ui.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ADetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val idFromViewModel = mutableStateOf(savedStateHandle.get<String>("id"))
}