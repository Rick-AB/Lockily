package com.example.lockily.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import com.example.lockily.presentation.home.component.AuthScreen
import com.example.lockily.presentation.home.component.HomeScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun HomeFragment(viewModel: MainViewModel, navigator: DestinationsNavigator) {
    val isAuthenticated = viewModel.isAuthenticated.value
    val state = viewModel.state
    AnimatedVisibility(
        visible = isAuthenticated, enter = slideInHorizontally(
            animationSpec = tween(
                delayMillis = 500,
                easing = LinearEasing
            )
        )
    ) {
        HomeScreen(state.value, navigator)
    }
    AnimatedVisibility(
        visible = !isAuthenticated, exit = slideOutHorizontally(
            animationSpec = tween(
                easing = LinearEasing
            )
        )
    ) {
        AuthScreen(viewModel::onEvent)
    }
}