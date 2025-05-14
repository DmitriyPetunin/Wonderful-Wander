package com.android.practise.wonderfulwander.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.android.practise.wonderfulwander.R
import com.example.base.state.SignInResult
import com.example.base.state.UserData
import com.example.presentation.googleclient.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException
import com.example.base.R as baseR

class GoogleAuthUiClientImpl(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val auth:FirebaseAuth
): GoogleAuthUiClient {

    override suspend fun signIn(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(buildSignInRequest()).await()
            result.pendingIntent?.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    override suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }


    override fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    override suspend fun login(email: String, password: String): SignInResult {
        return try {
            val user = auth.signInWithEmailAndPassword(email, password).await().user

            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(data = null, errorMessage = e.message)
        }
    }

    override suspend fun register(email: String, password: String) {
        val user = auth.createUserWithEmailAndPassword(email, password).await().user
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(baseR.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}