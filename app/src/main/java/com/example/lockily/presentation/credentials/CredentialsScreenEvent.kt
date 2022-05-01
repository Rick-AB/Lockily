package com.example.lockily.presentation.credentials

sealed class CredentialsScreenEvent {
    data class OnScreenLoadEvent(val state: CredentialScreenState): CredentialsScreenEvent()
}