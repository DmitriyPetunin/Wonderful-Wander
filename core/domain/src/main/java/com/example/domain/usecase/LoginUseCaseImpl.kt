package com.example.domain.usecase

import com.example.base.model.user.login.LoginResult
import com.example.base.model.user.login.LoginUserParam
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl(
    private val userRepository: UserRepository
) : LoginUseCase {
    override suspend fun invoke(inputParam: LoginUserParam): Result<LoginResult> {
        return userRepository.login(inputParam)
    }
}