package com.example.lockily

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lockily.presentation.home.HomeFragment
import com.example.lockily.presentation.home.HomeScreenEvent
import com.example.lockily.presentation.home.MainViewModel
import com.example.lockily.ui.theme.LockilyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LockilyTheme {
                val mainViewModel: MainViewModel = hiltViewModel()
                viewModel = mainViewModel
                HomeFragment(viewModel)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onEvent(HomeScreenEvent.OnActivityPauseEvent)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LockilyTheme {

    }
}