package com.example.lockily.domain.models

import com.example.lockily.data.local.entities.CredentialEntity

data class Credential(
    val name: String,
    val password: String,
    val credentialType: String
) {
    internal fun toEntityModel() =
        CredentialEntity(name = name, password = password, credentialType = credentialType)
}