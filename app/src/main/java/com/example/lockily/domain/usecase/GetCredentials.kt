package com.example.lockily.domain.usecase

import com.example.lockily.domain.repository.LockilyRepo
import javax.inject.Inject

class GetCredentials @Inject constructor(
    private val lockilyRepo: LockilyRepo
) {
    operator fun invoke() = lockilyRepo.getCredentials()
}