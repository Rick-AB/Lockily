package com.example.lockily.presentation.home

import com.example.lockily.domain.models.Credential

data class HomeScreenState(
    val error: String? = null,
    val appCredentials: List<Credential> = emptyList(),
    val websiteCredentials: List<Credential> = emptyList(),
    val manuallyAddedCredentials: List<Credential> = emptyList()
) {
}