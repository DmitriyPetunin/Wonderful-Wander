package com.example.network.service.auth

import com.example.network.model.user.auth.TokenResponse
import com.example.network.model.user.login.req.LoginRequestParam
import com.example.network.model.user.login.res.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/refresh-token")
    suspend fun refreshAccessToken(
        @Body requestToken: String
    ): TokenResponse

    @GET("/api/auth/login")
    suspend fun login(input: LoginRequestParam): LoginResponse
}