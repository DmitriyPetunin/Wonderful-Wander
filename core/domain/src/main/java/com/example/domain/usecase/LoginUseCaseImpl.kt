package com.example.domain.usecase

import com.example.base.model.user.LoginResponse
import com.example.base.model.user.LoginUserParam
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
):LoginUseCase {
    override suspend fun invoke(inputParam: LoginUserParam): LoginResponse {
        return userRepository.login(inputParam)
    }
}