package com.example.domain.usecaseimpl

import com.example.domain.model.user.register.RegisterResult
import com.example.domain.model.user.register.RegisterUserParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.RegisterUseCase

class RegisterUseCaseImpl(
    private val userRepository: UserRepository
): RegisterUseCase {
    override suspend fun invoke(inputParam: RegisterUserParam): Result<RegisterResult> {
        return userRepository.register(inputParam)
    }
}