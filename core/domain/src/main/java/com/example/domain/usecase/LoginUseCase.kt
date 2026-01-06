package com.example.domain.usecase

import com.example.domain.model.user.login.LoginResult
import com.example.domain.model.user.login.LoginUserParam


interface LoginUseCase {
    suspend fun invoke(inputParam: LoginUserParam): Result<LoginResult>
}