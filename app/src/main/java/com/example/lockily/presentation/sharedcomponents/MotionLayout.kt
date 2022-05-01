package com.example.lockily.presentation.sharedcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.lockily.R
import com.example.lockily.presentation.home.component.SwipingStates
import com.example.lockily.ui.theme.LightGrey

@Composable
fun MotionLayoutContent(
    title: String,
    swipingState: SwipeableState<SwipingStates>,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.home_motion_scene).readBytes().decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = if (swipingState.progress.to == SwipingStates.COLLAPSED) swipingState.progress.fraction else 1f - swipingState.progress.fraction,
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGrey)
    ) {
        val properties = motionProperties(id = "title")
        Box(
            modifier = Modifier
                .layoutId("collapsing_header")
                .background(LightGrey)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.h4.copy(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
            ),
            fontSize = properties.value.fontSize("textSize"),
            modifier = Modifier.layoutId("title")
        )
        Column(modifier = Modifier.layoutId("content")) {
            content()
        }
    }
}
