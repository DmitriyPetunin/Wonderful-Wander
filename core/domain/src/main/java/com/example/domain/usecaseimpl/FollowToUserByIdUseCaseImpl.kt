package com.example.domain.usecaseimpl

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.FollowToUserByIdUseCase

class FollowToUserByIdUseCaseImpl(
    private val userRepository: UserRepository
): FollowToUserByIdUseCase {
    override suspend fun invoke(id: String): Result<Unit> {
        return userRepository.followToUserById(id = id)
    }
}