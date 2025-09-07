package com.example.secureshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.secureshare.presentation.Dashboard
import com.example.secureshare.presentation.HomeAuth
import com.example.secureshare.presentation.Login
import com.example.secureshare.presentation.OtpVerification
import com.example.secureshare.presentation.Screen
import com.example.secureshare.presentation.auth.HomeAuthViewmodel
import com.example.secureshare.ui.theme.SecureShareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            SecureShareTheme {

                val homeAuthViewmodel = viewModel<HomeAuthViewmodel>()

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.HomeAuth.route){
                    composable(Screen.HomeAuth.route){
                        HomeAuth(modifier = Modifier,homeAuthViewmodel,navController)
                    }
                    composable(Screen.Dashboard.route){
                        Dashboard()
                    }
                }
            }
        }
    }
}