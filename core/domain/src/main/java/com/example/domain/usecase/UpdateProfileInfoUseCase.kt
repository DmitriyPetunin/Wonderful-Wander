package com.example.domain.usecase

import com.example.domain.model.user.profile.ProfileInfoResult
import com.example.domain.model.user.profile.UpdateProfileParam


interface UpdateProfileInfoUseCase {
    suspend fun invoke(param: UpdateProfileParam): Result<ProfileInfoResult>
}