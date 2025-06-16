package com.example.network.service.auth

import com.example.network.model.user.auth.TokenResponse
import com.example.network.model.user.login.req.LoginRequest
import com.example.network.model.user.login.res.LoginResponse
import com.example.network.model.user.register.req.RegisterUserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/refresh-token")
    suspend fun refreshAccessToken(
        @Body refreshToken: String
    ): Response<TokenResponse>

    @POST("/api/auth/login")
    suspend fun login(
        @Body input: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun register(
        @Body input: RegisterUserRequest
    ): Response<LoginResponse>
}