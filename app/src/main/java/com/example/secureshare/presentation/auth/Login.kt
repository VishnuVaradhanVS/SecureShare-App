package com.example.secureshare.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secureshare.presentation.auth.HomeAuthComponent
import com.example.secureshare.presentation.auth.HomeAuthViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Login(modifier: Modifier = Modifier, homeAuthViewmodel: HomeAuthViewmodel) {
    val userName = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val showPassword = rememberSaveable {
        mutableStateOf(false)
    }
    val errorLogin = rememberSaveable {
        mutableStateOf(false)
    }

    val visibleLogin = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visibleLogin.value = true
    }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = visibleLogin.value,
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
                        "LOGIN",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(16.dp))
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userName.value,
                        onValueChange = {
                            userName.value = it
                        },
                        placeholder = { Text("Username") },
                        supportingText = {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Text(
                                    "Forgot Username?",
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.clickable(
                                        onClick = {
                                            coroutineScope.launch {
                                                visibleLogin.value = !visibleLogin.value
                                                delay(800)
                                                homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.FORGOTUSERNAME
                                            }

                                        }
                                    )
                                )
                            }
                        },
                        shape = RoundedCornerShape(32.dp),
                        singleLine = true
                    )
                    Spacer(Modifier.height(24.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password.value,
                        onValueChange = {
                            password.value = it
                        },
                        placeholder = { Text("Password") },
                        singleLine = true,
                        supportingText = {
                            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.End) {
                                Text(
                                    "Forgot Password?",
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.clickable(onClick = {
                                        coroutineScope.launch {
                                            visibleLogin.value = !visibleLogin.value
                                            delay(800)
                                            homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.FORGOTPASSWORD
                                        }
                                    })
                                )
                            }
                        },
                        shape = RoundedCornerShape(32.dp),
                        suffix = {
                            Icon(
                                imageVector = if (showPassword.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle password",
                                modifier = Modifier.clickable(onClick = {
                                    showPassword.value = !showPassword.value
                                }), tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation()
                    )
                    Spacer(Modifier.height(12.dp))
                    if (errorLogin.value) {
                        Text("Invalid credentials", color = Color.Red)
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            coroutineScope.launch {
                                visibleLogin.value = !visibleLogin.value
                                delay(800)
                                homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.OTP
                            }
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Login")
                    }
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        coroutineScope.launch {
                            visibleLogin.value = !visibleLogin.value
                            delay(800)
                            homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.SIGNUP
                        }
                    }, border = BorderStroke(2.dp,
                        color = MaterialTheme.colorScheme.primary
                    )) {
                        Text("Sign up")
                    }
                }
            }

        }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    Login(
        modifier = Modifier,
        homeAuthViewmodel = HomeAuthViewmodel()
    )
}