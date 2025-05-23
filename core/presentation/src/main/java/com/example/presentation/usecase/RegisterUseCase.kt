package com.example.presentation.usecase

import com.example.base.model.user.register.RegisterResult
import com.example.base.model.user.register.RegisterUserParam

interface RegisterUseCase {
    suspend fun invoke(inputParam: RegisterUserParam): Result<RegisterResult>
}