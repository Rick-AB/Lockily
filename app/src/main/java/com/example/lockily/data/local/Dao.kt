package com.example.lockily.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lockily.data.local.entities.CredentialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCredential(credentialEntity: CredentialEntity)

    @Query("SELECT * FROM CredentialEntity")
    fun getCredentials(): Flow<List<CredentialEntity>>
}