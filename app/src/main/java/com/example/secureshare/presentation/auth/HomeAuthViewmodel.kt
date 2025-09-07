package com.example.secureshare.presentation.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeAuthViewmodel : ViewModel(){
    var homeAuthComponent = mutableStateOf(HomeAuthComponent.LOGIN)

    var validAccount : MutableState<Boolean> = mutableStateOf(false)
}