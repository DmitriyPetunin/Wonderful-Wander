package com.example.domain.usecase

import com.example.domain.model.user.People


interface GetAllFollowingUseCase {
    suspend fun invoke(page:Int = 0,limit:Int = 10):Result<List<People>>
}