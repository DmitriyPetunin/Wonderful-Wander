package com.example.domain.usecase

import com.example.base.model.user.ProfileInfo
import com.example.base.model.user.UpdateProfileParam
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.UpdateProfileInfoUseCase

class UpdateProfileInfoUseCaseImpl(
    private val userRepository: UserRepository
): UpdateProfileInfoUseCase {
    override suspend fun invoke(param: UpdateProfileParam): Result<ProfileInfo> {
        return userRepository.getProfileInfo()
    }
}