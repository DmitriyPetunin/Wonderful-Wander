package com.example.domain.usecaseimpl

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.DeleteUserProfileUseCase

class DeleteUserProfileUseCaseImpl(
    private val userRepository: UserRepository
): DeleteUserProfileUseCase {
    override suspend fun invoke(): Result<Unit> {
        return userRepository.deleteProfile()
    }
}