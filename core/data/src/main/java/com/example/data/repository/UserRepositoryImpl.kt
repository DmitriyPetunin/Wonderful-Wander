package com.example.data.repository

import com.example.base.model.user.LoginResponse
import com.example.base.model.user.LoginUserParam
import com.example.base.model.user.RegisterResponse
import com.example.base.model.user.RegisterUserParam
import com.example.domain.repository.UserRepository
import com.example.network.service.user.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
): UserRepository {
    override suspend fun login(inputParam: LoginUserParam): LoginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun register(inputParam: RegisterUserParam): RegisterResponse {
        TODO("Not yet implemented")
    }
}