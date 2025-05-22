package com.example.network.service.auth

import com.example.network.model.user.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/auth/refresh-token")
    suspend fun refreshAccessToken(
        @Body requestToken: String
    ): Response<TokenResponse>
}