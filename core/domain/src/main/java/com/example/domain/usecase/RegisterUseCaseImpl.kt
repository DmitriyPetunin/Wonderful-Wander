package com.example.domain.usecase

import com.example.base.model.user.register.RegisterResult
import com.example.base.model.user.register.RegisterUserParam
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
):RegisterUseCase {
    override suspend fun invoke(inputParam: RegisterUserParam): Result<RegisterResult> {
        return userRepository.register(inputParam)
    }
}