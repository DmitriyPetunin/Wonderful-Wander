package com.android.practise.wonderfulwander.di

import android.content.Context
import com.android.practise.wonderfulwander.sign_in.GoogleAuthUiClientImpl
import com.example.presentation.googleclient.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context,
        authFirebase: FirebaseAuth
    ): GoogleAuthUiClient {
        return GoogleAuthUiClientImpl(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
            auth = authFirebase
        )
    }

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(): FirebaseAnalytics {
        return Firebase.analytics
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}