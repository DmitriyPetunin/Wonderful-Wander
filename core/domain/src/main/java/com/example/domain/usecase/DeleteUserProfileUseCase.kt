package com.example.domain.usecase

interface DeleteUserProfileUseCase {
    suspend fun invoke(): Result<Unit>
}