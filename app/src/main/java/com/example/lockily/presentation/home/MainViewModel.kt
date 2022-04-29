package com.example.lockily.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lockily.domain.models.Credential
import com.example.lockily.domain.usecase.GetCredentials
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCredentials: GetCredentials
) : ViewModel() {

    private val _isAuthenticated = mutableStateOf(false)
    val isAuthenticated = _isAuthenticated
    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    init {
        loadCredentials()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnAuthenticationEvent -> _isAuthenticated.value = true
            is HomeScreenEvent.OnActivityPauseEvent -> _isAuthenticated.value = false
        }
    }

    private fun loadCredentials() {
        getCredentials().onEach {
            filterCredentialsByType(it)
        }.launchIn(viewModelScope)
    }

    private fun filterCredentialsByType(credentialsList: List<Credential>) {
        if (credentialsList.isNullOrEmpty()) {
            return
        }

        val appCredentials = credentialsList.filter { it.credentialType == "App" }
        val websitesCredentials = credentialsList.filter { it.credentialType == "Web" }
        val manuallyAddedCredentials = credentialsList.filter { it.credentialType == "Manual" }

        _state.value = _state.value.copy(
            appCredentials = appCredentials,
            websiteCredentials = websitesCredentials,
            manuallyAddedCredentials = manuallyAddedCredentials
        )
    }
}