package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.DeleteUserProfileUseCase
import javax.inject.Inject

class DeleteUserProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): DeleteUserProfileUseCase {
    override suspend fun invoke(): Result<Unit> {
        return userRepository.deleteProfile()
    }
}