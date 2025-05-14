package com.example.network

import android.content.Context
import com.auth0.android.jwt.JWT
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.minutes

class SessionManager(
    context: Context
) {

    private var sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    // Method to check if the access token has expired
    fun isAccessTokenExpired(): Boolean {
        val currentDate = Date()

        val token = getAccessToken()
        val fifteenMinutesInMillis = TimeUnit.MINUTES.toMillis(15)

        val accessTokenExpirationTime = extractTokenExpiration(token)

        return accessTokenExpirationTime != null && accessTokenExpirationTime.time - currentDate.time >= fifteenMinutesInMillis

    }

    fun extractTokenExpiration(token: String): Date? {
        try {
            val jwt = JWT(token)

            val expirationTime: Date? = jwt.getExpiresAt()

            return expirationTime
        } catch (e: Exception) {
            e.printStackTrace()
            println("Ошибка при декодировании токена: ${e.message}")
            return null
        }
    }

    fun saveAccessToken(token:String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN,token).apply()
    }

    fun saveRefreshToken(token:String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()
    }


    fun getAccessToken():String{
        return sharedPreferences.getString(ACCESS_TOKEN,DEFAULT_NAME_ACCESS_TOKEN) ?:DEFAULT_NAME_ACCESS_TOKEN
    }

    fun getRefreshToken():String{
        return sharedPreferences.getString(REFRESH_TOKEN, DEFAULT_NAME_REFRESH_TOKEN) ?:DEFAULT_NAME_REFRESH_TOKEN
    }

    companion object{
        const val SHARED_PREFS_NAME = "shared_prefs_name"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val DEFAULT_NAME_ACCESS_TOKEN = "default_access"
        const val DEFAULT_NAME_REFRESH_TOKEN = "default_refresh"
    }
}