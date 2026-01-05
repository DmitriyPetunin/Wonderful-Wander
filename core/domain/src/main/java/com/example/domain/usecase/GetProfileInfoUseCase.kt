package com.example.domain.usecase

import com.example.base.model.user.profile.ProfileInfoResult

interface GetProfileInfoUseCase {
    suspend fun invoke():Result<ProfileInfoResult>
}