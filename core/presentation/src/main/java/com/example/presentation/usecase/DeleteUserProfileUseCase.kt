package com.example.presentation.usecase

interface DeleteUserProfileUseCase {
    suspend fun invoke(): Result<Unit>
}