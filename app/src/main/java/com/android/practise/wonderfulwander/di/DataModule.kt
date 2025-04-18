package com.android.practise.wonderfulwander.di

import android.app.Application
import android.content.Context
import com.android.practise.wonderfulwander.sign_in.GoogleAuthUiClient
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(@ApplicationContext context:Context):GoogleAuthUiClient{
        return GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }
}