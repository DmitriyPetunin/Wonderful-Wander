package com.example.domain.di

import com.example.domain.repository.GeoRepository
import com.example.domain.usecase.GetActualGeoDataUseCase
import com.example.domain.usecase.GetActualGeoDataUseCaseImpl
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
}