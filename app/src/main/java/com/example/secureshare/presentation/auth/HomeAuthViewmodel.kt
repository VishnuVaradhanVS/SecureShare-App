package com.example.secureshare.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class HomeAuthViewmodel : ViewModel(){
    var homeAuthComponent = mutableStateOf(HomeAuthComponent.LOGIN)

    var _validAccount = mutableStateOf(false)
    var validAccount : State<Boolean> = _validAccount
}