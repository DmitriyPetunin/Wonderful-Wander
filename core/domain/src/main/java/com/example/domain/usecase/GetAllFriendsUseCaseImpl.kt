package com.example.domain.usecase

import com.example.base.model.user.friends.Friend
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetAllFriendsUseCase

class GetAllFriendsUseCaseImpl(
    val userRepository: UserRepository
): GetAllFriendsUseCase {
    override suspend fun invoke(): List<Friend> {
        return userRepository.getAllFriends()
    }
}