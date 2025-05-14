package com.example.domain.repository

import com.example.base.model.user.LoginResponse
import com.example.base.model.user.LoginUserParam
import com.example.base.model.user.RegisterResponse
import com.example.base.model.user.RegisterUserParam

interface UserRepository {

    suspend fun login(inputParam:LoginUserParam):LoginResponse

    suspend fun register(inputParam: RegisterUserParam):RegisterResponse
}