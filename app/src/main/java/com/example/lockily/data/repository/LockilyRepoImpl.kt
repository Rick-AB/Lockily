package com.example.lockily.data.repository

import com.example.lockily.data.local.LockilyDatabase
import com.example.lockily.domain.models.Credential
import com.example.lockily.domain.repository.LockilyRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LockilyRepoImpl @Inject constructor(
    lockilyDatabase: LockilyDatabase
) : LockilyRepo {
    private val dao = lockilyDatabase.dao
    override fun addCredential(credential: Credential) {
        dao.insertCredential(credential.toEntityModel())
    }

    override fun getCredentials(): Flow<List<Credential>> {
        return dao.getCredentials().map { credentialList ->
            credentialList.map { it.toDomainModel() }
        }
    }
}