package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.UnFollowToUserByIdUseCase
import javax.inject.Inject

class UnFollowToUserByIdUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): UnFollowToUserByIdUseCase{
    override suspend fun invoke(id: String): Result<Unit> {
        return userRepository.unFollowToUserById(id)
    }
}