package com.example.secureshare.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.secureshare.presentation.auth.ForgotPassword
import com.example.secureshare.presentation.auth.ForgotUsername
import com.example.secureshare.presentation.auth.HomeAuthComponent
import com.example.secureshare.presentation.auth.HomeAuthViewmodel
import com.example.secureshare.presentation.auth.ResetPassword
import com.example.secureshare.presentation.auth.Signup
import com.example.secureshare.ui.theme.LightBlue

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeAuth(
    modifier: Modifier = Modifier,
    homeAuthViewmodel: HomeAuthViewmodel,
    navController: NavController
) {

    val currComponent by homeAuthViewmodel.homeAuthComponent

    val validAccount by homeAuthViewmodel.validAccount
    val visibleTitle = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visibleTitle.value = true
    }
    LaunchedEffect(validAccount) {
        if (validAccount) {
            navController.navigate(Screen.Dashboard.route)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(color = LightBlue),
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = visibleTitle.value,
                enter = slideInVertically(
                    initialOffsetY = { -it }, animationSpec = tween(800)
                ), exit = slideOutVertically(
                    targetOffsetY = { it }, animationSpec = tween(800)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {
                    Text(
                        "SECURE SHARE",
                        fontSize = 32.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            when(currComponent){
                HomeAuthComponent.LOGIN -> Login(modifier,homeAuthViewmodel)
                HomeAuthComponent.SIGNUP -> Signup(modifier,homeAuthViewmodel)
                HomeAuthComponent.OTP -> OtpVerification(modifier,homeAuthViewmodel)
                HomeAuthComponent.FORGOTPASSWORD -> ForgotPassword(modifier,homeAuthViewmodel)
                HomeAuthComponent.FORGOTUSERNAME -> ForgotUsername(modifier,homeAuthViewmodel)
                HomeAuthComponent.RESETPASSWORD -> ResetPassword(modifier,homeAuthViewmodel)
            }

        }
    }
    LaunchedEffect(validAccount) {
        if (validAccount) {
            navController.navigate(Screen.Dashboard.route)
        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomeAuthPreview(modifier: Modifier = Modifier) {
    HomeAuth(
        homeAuthViewmodel = HomeAuthViewmodel(),
        navController = NavController(LocalContext.current)
    )
}