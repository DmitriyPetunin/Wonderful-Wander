package com.example.domain.usecaseimpl

import com.example.domain.model.user.profile.ProfileInfoResult
import com.example.domain.model.user.profile.UpdateProfileParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UpdateProfileInfoUseCase

class UpdateProfileInfoUseCaseImpl(
    private val userRepository: UserRepository
): UpdateProfileInfoUseCase {
    override suspend fun invoke(param: UpdateProfileParam): Result<ProfileInfoResult> {
        return userRepository.getProfileInfo()
    }
}