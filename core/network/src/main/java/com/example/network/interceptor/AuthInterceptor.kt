package com.example.network.interceptor

import com.example.network.SessionManager
import com.example.network.service.auth.AuthService
import com.example.network.service.user.UserService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val accessToken = sessionManager.getAccessToken()

        if (accessToken != SessionManager.DEFAULT_NAME_ACCESS_TOKEN && sessionManager.isAccessTokenExpired()) {
            val refreshToken = sessionManager.getRefreshToken()



            // Make the token refresh request
            val refreshedToken = runBlocking {
                try {
                    val response = authService.refreshAccessToken(requestToken = refreshToken)

                    // Update the refreshed access token and its expiration time in the session
                    sessionManager.saveAccessToken(response.accessToken)
                    sessionManager.saveRefreshToken(response.refreshToken)
                    response.accessToken
                }catch (e: Exception){
                    e.printStackTrace()
                    throw RuntimeException(e.message)
                }
            }

            if (refreshToken != SessionManager.DEFAULT_NAME_REFRESH_TOKEN) {
                // Create a new request with the refreshed access token
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $refreshedToken")
                    .build()

                // Retry the request with the new access token
                return chain.proceed(newRequest)
            }
        }

        // Add the access token to the request header
        val authorizedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()

        return chain.proceed(authorizedRequest)

    }
}