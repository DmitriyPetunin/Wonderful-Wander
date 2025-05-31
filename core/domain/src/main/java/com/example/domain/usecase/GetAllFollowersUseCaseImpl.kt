package com.example.domain.usecase

import com.example.base.model.user.People
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetAllFollowersUseCase

class GetAllFollowersUseCaseImpl(
    private val userRepository: UserRepository
): GetAllFollowersUseCase {
    override suspend fun invoke(): Result<List<People>> {
        return userRepository.getAllFollowers()
    }
}