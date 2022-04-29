package com.example.lockily.domain.repository

import com.example.lockily.domain.models.Credential
import kotlinx.coroutines.flow.Flow

interface LockilyRepo {
    fun addCredential(credential: Credential)
    fun getCredentials(): Flow<List<Credential>>
}