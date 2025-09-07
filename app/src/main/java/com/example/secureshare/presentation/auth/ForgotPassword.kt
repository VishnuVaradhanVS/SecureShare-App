package com.example.secureshare.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForgotPassword(modifier: Modifier = Modifier,homeAuthViewmodel: HomeAuthViewmodel) {
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val otp = rememberSaveable {
        mutableStateOf("")
    }
    val visibleForgotPassword = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visibleForgotPassword.value = true
    }
    val visibleOtp = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = visibleForgotPassword.value,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(800)
        ), exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(800)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(32.dp, 32.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(32.dp), contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.imeNestedScroll(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        "RESET PASSWORD",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(if(visibleOtp.value) "Enter the otp to reset password" else "Enter your registered email to reset password")
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        placeholder = { Text("Enter Registered Email") },
                        shape = RoundedCornerShape(32.dp),
                        singleLine = true,
                        enabled = !visibleOtp.value
                    )
                    Spacer(Modifier.height(24.dp))
                    if(visibleOtp.value){
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = otp.value,
                            onValueChange = {
                                otp.value = it
                            },
                            placeholder = { Text("OTP") },
                            shape = RoundedCornerShape(32.dp),
                            singleLine = true,
                        )
                        Spacer(Modifier.height(24.dp))
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                coroutineScope.launch {
                                    visibleForgotPassword.value = !visibleForgotPassword.value
                                    delay(800)
                                    homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.OTP
                                }
                            },
                            elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Resend OTP")
                        }
                        Spacer(Modifier.height(24.dp))
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            coroutineScope.launch {
                                if(visibleOtp.value){
                                    visibleForgotPassword.value = !visibleForgotPassword.value
                                    delay(800)
                                    homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.RESETPASSWORD
                                }else{
                                    visibleOtp.value = !visibleOtp.value
                                }
                            }
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text(if(visibleOtp.value)"Verify" else "Send OTP")
                    }
                }
            }

        }
    }
}