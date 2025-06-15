package com.example.domain.usecase

import com.example.base.model.user.profile.ProfileInfoResult
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetProfileInfoUseCase

class GetProfileInfoUseCaseImpl(
    private val userRepository: UserRepository
): GetProfileInfoUseCase {
    override suspend fun invoke(): Result<ProfileInfoResult> {
        return userRepository.getProfileInfo()
    }
}