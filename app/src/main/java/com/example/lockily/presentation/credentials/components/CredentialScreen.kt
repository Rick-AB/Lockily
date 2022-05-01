package com.example.lockily.presentation.credentials.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lockily.R
import com.example.lockily.domain.models.Credential
import com.example.lockily.presentation.credentials.CredentialScreenState
import com.example.lockily.presentation.home.component.SwipingStates
import com.example.lockily.presentation.home.component.getNestedScrollConnection
import com.example.lockily.presentation.sharedcomponents.MotionLayoutContent
import com.example.lockily.ui.theme.LightGrey

@Composable
fun CredentialsScreen(
    state: CredentialScreenState,
    initialTab: String
) {
    val currentTab = remember {
        mutableStateOf(initialTab)
    }
    val currentList = when (currentTab.value) {
        "Apps" -> state.appCredentials
        "Websites" -> state.websiteCredentials
        "Manual" -> state.manuallyAddedCredentials
        else -> emptyList()
    }
    val onItemClick: (String) -> Unit = {
        currentTab.value = it
    }
    val swipingState = rememberSwipeableState(initialValue = SwipingStates.EXPANDED)
    val connection = getNestedScrollConnection(swipingState = swipingState)
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
            MotionLayoutContent(
                title = stringResource(id = R.string.ids_and_password),
                swipingState = swipingState
            ) {
                CredentialContent(currentList, onItemClick)
            }
        }
    }
}

@Composable
fun CredentialContent(currentList: List<Credential>, onItemClick: (String) -> Unit) {
    val bottomBarItems = listOf("Apps", "Websites", "Manual")
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        LockilyBottomBar(
            currentSelection = "App",
            items = bottomBarItems,
            onItemClick = onItemClick
        )
    }) {
        if (currentList.isNullOrEmpty()) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.manual_add_prompt),
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 32.dp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(onClick = { /*TODO*/ }) {

                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
                items(currentList) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_round_apps_24),
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "", style = MaterialTheme.typography.body1)
                            Text(text = "", style = MaterialTheme.typography.caption)
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LockilyBottomBar(
    currentSelection: String,
    items: List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
) {
    BottomNavigation(modifier = modifier, backgroundColor = LightGrey) {
        items.forEach { item ->
            val selected = item == currentSelection
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Black,
                unselectedContentColor = MaterialTheme.colors.onBackground,
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = item, style = TextStyle(fontSize = 14.sp))
                        if (selected) {
                            Divider(
                                thickness = 2.dp, modifier = Modifier
                                    .width(10.dp)
                                    .background(
                                        Color.Black, RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    }
                }
            )
        }
    }
}