package com.example.presentation.usecase

import com.example.base.model.user.login.LoginResult
import com.example.base.model.user.login.LoginUserParam

interface LoginUseCase {
    suspend fun invoke(inputParam: LoginUserParam): Result<LoginResult>
}