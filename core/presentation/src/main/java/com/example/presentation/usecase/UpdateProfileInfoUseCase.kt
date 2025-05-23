package com.example.presentation.usecase

import com.example.base.model.user.profile.ProfileInfoResult
import com.example.base.model.user.profile.UpdateProfileParam

interface UpdateProfileInfoUseCase {
    suspend fun invoke(param: UpdateProfileParam): Result<ProfileInfoResult>
}