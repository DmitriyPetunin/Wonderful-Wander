package com.example.base

import android.content.Context
import android.util.Log
import com.auth0.android.jwt.JWT
import java.util.Date
import javax.inject.Inject

class SessionManager (
    context: Context
) {

    private var sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)


    fun isAccessTokenExpired(): Boolean {
        val currentDate = Date()

        val token = getAccessToken()

        val accessTokenExpirationTime = extractTokenExpiration(token)

        return accessTokenExpirationTime != null && accessTokenExpirationTime.time - currentDate.time >= FIFTEEN_MINUTES

    }

    private fun extractTokenExpiration(token: String): Date? {
        try {
            val jwt = JWT(token)

            val expirationTime: Date? = jwt.getExpiresAt()

            return expirationTime
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("TEST-TAG","Ошибка при декодировании токена: ${e.message}")
            return null
        }
    }

    fun saveUserId(id: String){
        sharedPreferences.edit().putString(USER_ID,id).apply()
    }

    fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()
    }


    fun getUserId(): String {
        return sharedPreferences.getString(
            USER_ID,
            DEFAULT_USER_ID
        ) ?: DEFAULT_USER_ID
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(
            ACCESS_TOKEN,
            DEFAULT_NAME_ACCESS_TOKEN
        ) ?: DEFAULT_NAME_ACCESS_TOKEN
    }

    fun getRefreshToken(): String {
        return sharedPreferences.getString(REFRESH_TOKEN, DEFAULT_NAME_REFRESH_TOKEN)
            ?: DEFAULT_NAME_REFRESH_TOKEN
    }


    fun clearSession(){
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        const val FIFTEEN_MINUTES = 15 * 60 * 1000L
        const val SHARED_PREFS_NAME = "shared_prefs_name"
        const val USER_ID = "user_id"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val DEFAULT_USER_ID = "default_user_id"
        const val DEFAULT_NAME_ACCESS_TOKEN = "default_access"
        const val DEFAULT_NAME_REFRESH_TOKEN = "default_refresh"
    }

}