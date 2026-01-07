package com.example.domain.usecase

interface UnFollowToUserByIdUseCase {
    suspend fun invoke(id:String):Result<Unit>
}