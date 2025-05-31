package com.example.presentation.usecase

import com.example.base.model.user.People

interface GetAllFollowingUseCase {
    suspend fun invoke():Result<List<People>>
}