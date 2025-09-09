package com.example.secureshare.presentation.appScreens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel

class AppScreenViewModel : ViewModel(){
    var currentSelected = mutableStateOf("Dashboard")
}