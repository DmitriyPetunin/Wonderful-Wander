package com.example.presentation.usecase

interface FollowToUserByIdUseCase {
    suspend fun invoke(id:String):Result<Unit>
}