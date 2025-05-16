package com.example.presentation.usecase

import com.example.base.model.user.friends.Friend

interface GetAllFriendsUseCase {
    suspend fun invoke():List<Friend>
}