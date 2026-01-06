package com.example.domain.usecase

import com.example.domain.model.user.profile.ProfileInfoResult


interface GetProfileInfoUseCase {
    suspend fun invoke():Result<ProfileInfoResult>
}