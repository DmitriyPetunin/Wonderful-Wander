package com.example.presentation.usecase

import com.example.base.model.user.RegisterResponse
import com.example.base.model.user.RegisterUserParam

interface RegisterUseCase {
    suspend fun invoke(inputParam:RegisterUserParam):RegisterResponse
}