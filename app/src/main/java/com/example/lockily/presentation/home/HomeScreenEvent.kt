package com.example.lockily.presentation.home

sealed class HomeScreenEvent {
    object OnAuthenticationEvent : HomeScreenEvent()
    object OnActivityPauseEvent: HomeScreenEvent()
}
