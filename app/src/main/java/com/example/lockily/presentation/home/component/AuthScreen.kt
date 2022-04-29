package com.example.lockily.presentation.home.component

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.lockily.R
import com.example.lockily.presentation.home.HomeScreenEvent

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun AuthScreen(
    onEvent: (HomeScreenEvent) -> Unit
) {
    val biometricPrompt = prepareAuthenticator(onEvent)
    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle(stringResource(id = R.string.biometric_prompt))
        .setSubtitle(stringResource(id = R.string.scan_fingerprint_prompt))
        .setAllowedAuthenticators(BIOMETRIC_STRONG)
        .setNegativeButtonText(stringResource(id = R.string.use_password))
        .build()

    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.lockily_pass),
            style = MaterialTheme.typography.h3,
            fontFamily = MaterialTheme.typography.h3.fontFamily,
            modifier = Modifier
                .padding(top = 80.dp)
                .align(Alignment.TopCenter),
            textAlign = TextAlign.Center
        ) // TODO override material theme for clean theming

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_fingerprint_24),
                contentDescription = "",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { biometricPrompt.authenticate(promptInfo) },
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.tap_to_scan),
                style = MaterialTheme.typography.caption.copy(fontSize = 16.sp),
                textAlign = TextAlign.Center,
            )
        }

    }
}

@Composable
fun prepareAuthenticator(onEvent: (HomeScreenEvent) -> Unit): BiometricPrompt {
    val canAuthenticate = remember {
        mutableStateOf(true)
    }
    val failedAuthErrorMsg = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current as FragmentActivity
    val biometricManager = BiometricManager.from(context)
    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
        BiometricManager.BIOMETRIC_SUCCESS ->
            canAuthenticate.value = true
        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
            failedAuthErrorMsg.value = "No biometric features available on this device."
            canAuthenticate.value = false
        }

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
            failedAuthErrorMsg.value = "Biometric features are currently unavailable."
            canAuthenticate.value = false
        }
        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
//            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
//                putExtra(
//                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
//                    BIOMETRIC_STRONG
//                )
//            }
//            context.startActivityForResult(enrollIntent, 1)
            Toast.makeText(context, "Damn!", Toast.LENGTH_LONG).show()
        }
        BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
            Toast.makeText(context, "Damn!", Toast.LENGTH_LONG).show()

        }
        BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
            Toast.makeText(context, "Damn!", Toast.LENGTH_LONG).show()

        }
        BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
            Toast.makeText(context, "Damn!", Toast.LENGTH_LONG).show()
        }
    }

    val biometricPrompt = BiometricPrompt(context, object :
        BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onEvent(HomeScreenEvent.OnAuthenticationEvent)
        }
    })
    return biometricPrompt
}