package com.example.domain.usecase

import com.example.base.model.user.friends.Friend
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetAllFriendsUseCase

class GetAllFriendsUseCaseImpl(
    private val userRepository: UserRepository
): GetAllFriendsUseCase {
    override suspend fun invoke(): Result<List<Friend>> {
        return userRepository.getAllFriends()
    }
}