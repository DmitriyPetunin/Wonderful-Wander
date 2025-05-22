package com.example.presentation.googleclient

import android.content.Intent
import android.content.IntentSender
import com.example.base.state.SignInResult
import com.example.base.state.UserData

interface GoogleAuthUiClient {

    suspend fun signIn(): IntentSender?

    suspend fun signInWithIntent(intent: Intent): SignInResult

    suspend fun signOut()

    fun getSignedInUser():UserData?
}