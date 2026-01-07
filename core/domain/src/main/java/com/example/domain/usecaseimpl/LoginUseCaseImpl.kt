package com.example.domain.usecaseimpl

import com.example.domain.model.user.login.LoginResult
import com.example.domain.model.user.login.LoginUserParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.LoginUseCase

class LoginUseCaseImpl(
    private val userRepository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(inputParam: LoginUserParam): Result<LoginResult> {
        return userRepository.login(inputParam)
    }
}