package com.example.domain.usecase

import com.example.base.model.user.RegisterResponse
import com.example.base.model.user.RegisterUserParam
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.RegisterUseCase
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
):RegisterUseCase {
    override suspend fun invoke(inputParam: RegisterUserParam): Result<RegisterResponse> {
        return userRepository.register(inputParam)
    }
}