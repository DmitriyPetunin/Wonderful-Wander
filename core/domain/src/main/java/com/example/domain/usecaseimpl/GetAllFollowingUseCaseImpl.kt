package com.example.domain.usecaseimpl

import com.example.domain.model.user.People
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetAllFollowingUseCase

class GetAllFollowingUseCaseImpl(
    private val userRepository: UserRepository
): GetAllFollowingUseCase {
    override suspend fun invoke(page:Int,limit:Int): Result<List<People>> {
        return userRepository.getAllFollowing(page,limit)
    }
}