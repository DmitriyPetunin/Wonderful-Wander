package com.example.presentation.usecase

import com.example.base.model.user.LoginResponse
import com.example.base.model.user.LoginUserParam

interface LoginUseCase {
    suspend fun invoke(inputParam:LoginUserParam): LoginResponse
}