package com.example.presentation.usecase

import com.example.base.model.user.People

interface GetAllFollowersUseCase {
    suspend fun invoke(page:Int = 0,limit:Int = 10):Result<List<People>>
}