package com.example.domain.usecaseimpl

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.UnFollowToUserByIdUseCase

class UnFollowToUserByIdUseCaseImpl (
    private val userRepository: UserRepository
): UnFollowToUserByIdUseCase {
    override suspend fun invoke(id: String): Result<Unit> {
        return userRepository.unFollowToUserById(id)
    }
}