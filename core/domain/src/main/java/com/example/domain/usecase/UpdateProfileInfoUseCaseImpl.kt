package com.example.domain.usecase

import com.example.base.model.user.profile.ProfileInfoResult
import com.example.base.model.user.profile.UpdateProfileParam
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.UpdateProfileInfoUseCase

class UpdateProfileInfoUseCaseImpl(
    private val userRepository: UserRepository
): UpdateProfileInfoUseCase {
    override suspend fun invoke(param: UpdateProfileParam): Result<ProfileInfoResult> {
        return userRepository.getProfileInfo()
    }
}