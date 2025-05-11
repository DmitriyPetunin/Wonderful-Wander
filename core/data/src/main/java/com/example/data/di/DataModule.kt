package com.example.data.di

import android.content.Context
import com.example.data.repository.GeoRepositoryImpl
import com.example.domain.repository.GeoRepository
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

}