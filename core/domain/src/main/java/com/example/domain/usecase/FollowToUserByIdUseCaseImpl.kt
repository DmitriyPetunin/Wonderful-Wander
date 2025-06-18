package com.example.domain.usecase

import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.FollowToUserByIdUseCase
import javax.inject.Inject

class FollowToUserByIdUseCaseImpl(
    private val userRepository: UserRepository
): FollowToUserByIdUseCase {
    override suspend fun invoke(id: String): Result<Unit> {
        return userRepository.followToUserById(id = id)
    }
}