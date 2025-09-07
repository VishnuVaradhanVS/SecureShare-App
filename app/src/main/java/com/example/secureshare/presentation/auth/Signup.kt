package com.example.secureshare.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Signup(modifier: Modifier = Modifier, homeAuthViewmodel: HomeAuthViewmodel) {
    val userName = rememberSaveable {
        mutableStateOf("")
    }
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val password = rememberSaveable {
        mutableStateOf("")
    }
    val showPassword = rememberSaveable {
        mutableStateOf(false)
    }
    val confirmPassword = rememberSaveable {
        mutableStateOf("")
    }
    val errorLogin = rememberSaveable {
        mutableStateOf(false)
    }


    val visibleSignup = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visibleSignup.value = true
    }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = visibleSignup.value,
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
                        "SIGNUP",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(48.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = userName.value,
                        onValueChange = {
                            userName.value = it
                        },
                        placeholder = { Text("Username") },
                        shape = RoundedCornerShape(32.dp),
                        singleLine = true
                    )
                    Spacer(Modifier.height(24.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email.value,
                        onValueChange = {
                            email.value = it
                        },
                        placeholder = { Text("Email") },
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
                    Spacer(Modifier.height(24.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = confirmPassword.value,
                        onValueChange = {
                            confirmPassword.value = it
                        },
                        placeholder = { Text("Confirm Password") },
                        singleLine = true,
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
                                visibleSignup.value=!visibleSignup.value
                                delay(800)
                                homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.LOGIN
                            }
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Create Account")
                    }
                }
            }
        }
    }
}