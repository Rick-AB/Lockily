package com.example.lockily.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lockily.domain.models.Credential

@Entity
data class CredentialEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val password: String,
    val credentialType: String
) {
    internal fun toDomainModel() = Credential(name, password, credentialType)
}