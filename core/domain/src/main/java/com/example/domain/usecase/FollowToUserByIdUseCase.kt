package com.example.domain.usecase

interface FollowToUserByIdUseCase {
    suspend fun invoke(id:String):Result<Unit>
}