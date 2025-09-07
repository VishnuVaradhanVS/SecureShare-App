package com.example.secureshare.presentation

sealed class Screen(val route: String) {
    object HomeAuth : Screen("home_auth")
    object Dashboard : Screen("dashboard")

}