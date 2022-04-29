package com.example.lockily.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.lockily.R
import com.example.lockily.presentation.home.CredentialItem
import com.example.lockily.presentation.home.HomeScreenState
import com.example.lockily.ui.theme.LightGrey
import timber.log.Timber

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@Composable
fun HomeScreen(state: HomeScreenState) {
    val credentialList = getCredentialList(state)
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.home_motion_scene).readBytes().decodeToString()
    }
    val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
    val connection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll( // Decides if use the scroll for parent (Swipe) or pass it to the children
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                return if (delta < 0) {
                    swipingState.performDrag(delta).toOffset()
                } else {
                    Offset.Zero
                }
            }

            override fun onPostScroll( // If there is any leftover scroll from children, let's try to use it on parent swipe
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                return swipingState.performDrag(delta).toOffset()
            }

            override suspend fun onPostFling( // Let's try to use fling on parent and pass all leftover to children
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                swipingState.performFling(velocity = available.y)
                return super.onPostFling(consumed, available)
            }

            private fun Float.toOffset() = Offset(0f, this)
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ -> FractionalThreshold(0.1f) },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to SwipingStates.COLLAPSED,
                        heightInPx to SwipingStates.EXPANDED,
                    )
                )
                .nestedScroll(connection)
        ) {
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
                    text = "Lockily Pass",
                    style = MaterialTheme.typography.h4.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                    ),
                    fontSize = properties.value.fontSize("textSize"),
                    modifier = Modifier.layoutId("title")
                )
                Column(modifier = Modifier.layoutId("content")) {
                    InfoSummaryCard(
                        title = stringResource(id = R.string.ids_and_password),
                        description = stringResource(
                            id = R.string.ids_and_passwords_desc
                        ),
                        credentialList = credentialList
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InfoSummaryCard(
                        title = stringResource(id = R.string.private_info),
                        description = stringResource(
                            id = R.string.private_info_desc
                        ),
                        credentialList = credentialList
                    )
                }
            }
        }
    }

}

@Composable
private fun getCredentialList(state: HomeScreenState): List<CredentialItem> {
    return listOf(
        CredentialItem(
            R.drawable.ic_round_apps_24,
            stringResource(id = R.string.apps),
            state.appCredentials.count()
        ),
        CredentialItem(
            R.drawable.ic_baseline_blur_on_24,
            stringResource(id = R.string.websites),
            state.websiteCredentials.count()
        ),
        CredentialItem(
            R.drawable.ic_baseline_add_circle_24,
            stringResource(id = R.string.manual_add),
            state.manuallyAddedCredentials.count()
        ),
    )
}
