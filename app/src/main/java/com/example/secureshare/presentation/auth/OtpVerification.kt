package com.example.secureshare.presentation

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.example.secureshare.presentation.auth.HomeAuthViewmodel
import com.example.secureshare.ui.theme.LightBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sin

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OtpVerification(modifier: Modifier = Modifier,homeAuthViewmodel: HomeAuthViewmodel) {

    val userEmail = "vishnuvaradhan2429@gmail.com"
    val userEmailHidden =
        userEmail[0] + "**" + userEmail[userEmail.indexOf('@') - 1] + userEmail.substring(
            userEmail.indexOf('@')
        )
    val otp = rememberSaveable {
        mutableStateOf("")
    }
    val visibleOtp = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visibleOtp.value=true
    }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = visibleOtp.value,
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
//            .fillMaxHeight(0.7f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(32.dp, 32.dp))
//            .background(color = Color.White)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
//            .border(2.dp, color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(32.dp, 32.dp))
            .padding(32.dp), contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.imeNestedScroll(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "OTP VERIFICATION",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
//                    color = LightBlue
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                Text("OTP has been sent to ${userEmailHidden}")
                Spacer(Modifier.height(16.dp))
                Spacer(Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = otp.value,
                    onValueChange = {
                        otp.value = it
                    },
                    placeholder = { Text("OTP") }, singleLine = true,
                    shape = RoundedCornerShape(32.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(12.dp))
                Spacer(Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        coroutineScope.launch {
                            visibleOtp.value = !visibleOtp.value
                            delay(800)
                            homeAuthViewmodel._validAccount.value = true
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
//                    colors = ButtonDefaults.buttonColors(LightBlue)
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Verify")
                }
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {

                }, border = BorderStroke(2.dp,
//                    color = LightBlue
                    color = MaterialTheme.colorScheme.primary
                )) {
                    Text("Resend OTP")
                }
            }
        }
    }
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun OtpVerificationPerview(modifier: Modifier = Modifier) {
    OtpVerification(
        modifier = Modifier,
        homeAuthViewmodel = HomeAuthViewmodel()
    )
}