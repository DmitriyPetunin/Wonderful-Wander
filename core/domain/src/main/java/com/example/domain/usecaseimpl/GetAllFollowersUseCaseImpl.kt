package com.example.domain.usecaseimpl

import com.example.domain.model.user.People
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetAllFollowersUseCase

class GetAllFollowersUseCaseImpl(
    private val userRepository: UserRepository
): GetAllFollowersUseCase {
    override suspend fun invoke(page:Int,limit:Int): Result<List<People>> {
        return userRepository.getAllFollowers(page,limit)
    }
}