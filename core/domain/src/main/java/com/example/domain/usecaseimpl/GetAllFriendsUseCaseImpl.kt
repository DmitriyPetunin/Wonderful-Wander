package com.example.domain.usecaseimpl

import com.example.domain.model.user.People
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetAllFriendsUseCase

class GetAllFriendsUseCaseImpl(
    private val userRepository: UserRepository
): GetAllFriendsUseCase {
    override suspend fun invoke(page:Int,limit:Int): Result<List<People>> {
        
        return userRepository.getAllFriends(page,limit)
    }
}