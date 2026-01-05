package com.example.domain.usecaseimpl

import com.example.base.model.user.profile.ProfileInfoResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetProfileInfoUseCase

class GetProfileInfoUseCaseImpl(
    private val userRepository: UserRepository
): GetProfileInfoUseCase {
    override suspend fun invoke(): Result<ProfileInfoResult> {
        return userRepository.getProfileInfo()
    }
}