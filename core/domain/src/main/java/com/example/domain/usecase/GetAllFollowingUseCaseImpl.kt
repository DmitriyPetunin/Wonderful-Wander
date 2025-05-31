package com.example.domain.usecase

import com.example.base.model.user.People
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetAllFollowingUseCase

class GetAllFollowingUseCaseImpl(
    private val userRepository: UserRepository
): GetAllFollowingUseCase {
    override suspend fun invoke(): Result<List<People>> {
        return userRepository.getAllFollowing()
    }
}