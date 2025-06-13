package com.example.presentation.usecase

interface UnFollowToUserByIdUseCase {
    suspend fun invoke(id:String):Result<Unit>
}