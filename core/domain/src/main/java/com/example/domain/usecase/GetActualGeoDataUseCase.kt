package com.example.domain.usecase

import com.example.domain.model.geo.ActualGeoLocationResult


interface GetActualGeoDataUseCase {
    suspend fun invoke(geocodeString:String): Result<ActualGeoLocationResult>
}