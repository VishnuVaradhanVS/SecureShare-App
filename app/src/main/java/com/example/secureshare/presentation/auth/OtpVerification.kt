package com.example.secureshare.presentation

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.secureshare.presentation.auth.HomeAuthViewmodel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
                    "OTP VERIFICATION",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                Text("OTP has been sent to ${userEmailHidden}")
                Spacer(Modifier.height(32.dp))
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
                Spacer(Modifier.height(32.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        coroutineScope.launch {
                            visibleOtp.value = !visibleOtp.value
                            delay(800)
                            homeAuthViewmodel.validAccount.value = true
                        }
                    },
                    elevation = ButtonDefaults.elevatedButtonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                ) {
                    Text("Verify")
                }
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 2.dp)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {

                }, border = BorderStroke(2.dp,
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