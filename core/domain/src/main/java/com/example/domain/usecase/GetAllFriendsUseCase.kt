package com.example.domain.usecase

import com.example.domain.model.user.People


interface GetAllFriendsUseCase {
    suspend fun invoke(page:Int = 1,limit:Int = 10):Result<List<People>>
}