package com.example.lockily.presentation.credentials

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lockily.presentation.credentials.components.CredentialsScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CredentialFragment(
    navigator: DestinationsNavigator,
    initialTab: String,
) {
    val viewModel: CredentialViewModel = hiltViewModel()
    CredentialsScreen(viewModel.state.value)
}