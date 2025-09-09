package com.example.secureshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secureshare.presentation.Dashboard
import com.example.secureshare.presentation.HomeAuth
import com.example.secureshare.presentation.Screen
import com.example.secureshare.presentation.appScreens.AppScreenViewModel
import com.example.secureshare.presentation.appScreens.dashboard.HomeScreen
import com.example.secureshare.presentation.auth.HomeAuthViewmodel
import com.example.secureshare.ui.theme.SecureShareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            SecureShareTheme {

                val homeAuthViewmodel = viewModel<HomeAuthViewmodel>()
                val appScreenViewModel = viewModel<AppScreenViewModel>()

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.HomeAuth.route){
                    composable(Screen.HomeAuth.route){
                        HomeAuth(modifier = Modifier,homeAuthViewmodel,navController)
                    }
                    composable(Screen.HomeScreen.route){
                        HomeScreen(appScreenViewModel = appScreenViewModel)
                    }
                }
            }
        }
    }
}