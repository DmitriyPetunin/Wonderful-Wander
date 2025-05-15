package com.example.domain.di

import com.example.domain.repository.GeoRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetActualGeoDataUseCaseImpl
import com.example.domain.usecase.RegisterUseCaseImpl
import com.example.presentation.usecase.GetActualGeoDataUseCase
import com.example.presentation.usecase.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {


    @Provides
    fun provideGetActualGeoDataUseCase(geoRepository: GeoRepository): GetActualGeoDataUseCase {
        return GetActualGeoDataUseCaseImpl(geoRepository = geoRepository)
    }

    @Provides
    fun provideRegisterUseCase(userRepository: UserRepository): RegisterUseCase {
        return RegisterUseCaseImpl(userRepository = userRepository)
    }
}