package com.example.presentation.usecase

import com.example.base.model.user.ProfileInfo
import com.example.base.model.user.UpdateProfileParam

interface UpdateProfileInfoUseCase {
    suspend fun invoke(param: UpdateProfileParam): Result<ProfileInfo>
}