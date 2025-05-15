package com.example.data.di

import com.example.data.repository.GeoRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.GeoRepository
import com.example.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {


    @Binds
    @Singleton
    fun bindGeoRepository(
        impl: GeoRepositoryImpl
    ): GeoRepository

    @Binds
    @Singleton
    fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

}