package com.example.presentation.usecase

import com.example.base.model.user.profile.ProfileInfoResult

interface GetProfileInfoUseCase {
    suspend fun invoke():Result<ProfileInfoResult>
}