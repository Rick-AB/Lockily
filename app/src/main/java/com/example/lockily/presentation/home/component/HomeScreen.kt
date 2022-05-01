package com.example.lockily.presentation.home.component

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.example.lockily.R
import com.example.lockily.presentation.destinations.CredentialFragmentDestination
import com.example.lockily.presentation.home.CredentialItem
import com.example.lockily.presentation.home.HomeScreenState
import com.example.lockily.presentation.sharedcomponents.MotionLayoutContent
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

enum class SwipingStates {
    EXPANDED,
    COLLAPSED
}

@Composable
fun HomeScreen(state: HomeScreenState, navigator: DestinationsNavigator) {
    val credentialList = getCredentialList(state)
    val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
    val connection = getNestedScrollConnection(swipingState)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val heightInPx = with(LocalDensity.current) { maxHeight.toPx() }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .swipeable(
                    state = swipingState,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Vertical,
                    anchors = mapOf(
                        0f to SwipingStates.COLLAPSED,
                        heightInPx to SwipingStates.EXPANDED,
                    )
                )
                .nestedScroll(connection)
        ) {
            MotionLayoutContent(stringResource(id = R.string.home_title), swipingState) {
                HomeContent(credentialList = credentialList, navigator)
            }
        }
    }

}

@Composable
fun HomeContent(credentialList: List<CredentialItem>, navigator: DestinationsNavigator) {
    InfoSummaryCard(
        title = stringResource(id = R.string.ids_and_password),
        description = stringResource(
            id = R.string.ids_and_passwords_desc
        ),
        credentialList = credentialList
    ) {
        navigator.navigate(CredentialFragmentDestination(it))
    }

    Spacer(modifier = Modifier.height(16.dp))

    InfoSummaryCard(
        title = stringResource(id = R.string.private_info),
        description = stringResource(
            id = R.string.private_info_desc
        ),
        credentialList = credentialList,
        {}
    )
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

@Composable
fun getNestedScrollConnection(swipingState: SwipeableState<SwipingStates>) = remember {
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
