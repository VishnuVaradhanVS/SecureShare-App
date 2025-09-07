package com.example.secureshare.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secureshare.ui.theme.LightBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ForgotUsername(modifier: Modifier = Modifier,homeAuthViewmodel: HomeAuthViewmodel) {
    val visibleForgotUsername = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visibleForgotUsername.value = true
    }
    val coroutineScope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = visibleForgotUsername.value,
        enter = slideInVertically(
            initialOffsetY = { it }, // full height → slide from bottom
            animationSpec = tween(800) // duration 600ms
        ), exit = slideOutVertically(
            targetOffsetY = { it }, // slide down when hidden
            animationSpec = tween(800)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(32.dp, 32.dp))
//                .background(color = Color.White)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
//                .border(
//                    2.dp, color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(32.dp, 32.dp)
//                )
                .padding(32.dp), contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.imeNestedScroll(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        "FORGOT USERNAME",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
//                        color = LightBlue
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(24.dp))
                    Text("Your username has been sent to your registred email")
                    Spacer(Modifier.height(24.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            coroutineScope.launch {
                                visibleForgotUsername.value = !visibleForgotUsername.value
                                delay(800)
                                homeAuthViewmodel.homeAuthComponent.value = HomeAuthComponent.LOGIN
                            }
                        },
                        elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
//                        colors = ButtonDefaults.buttonColors(LightBlue)
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Back to Login")
                    }
                }
            }


        }
    }
}