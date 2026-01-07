package com.example.domain.usecase

import com.example.domain.model.user.register.RegisterResult
import com.example.domain.model.user.register.RegisterUserParam


interface RegisterUseCase {
    suspend fun invoke(inputParam: RegisterUserParam): Result<RegisterResult>
}