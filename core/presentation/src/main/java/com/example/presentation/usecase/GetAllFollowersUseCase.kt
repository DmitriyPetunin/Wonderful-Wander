package com.example.presentation.usecase

import com.example.base.model.user.People

interface GetAllFollowersUseCase {
    suspend fun invoke():Result<List<People>>
}