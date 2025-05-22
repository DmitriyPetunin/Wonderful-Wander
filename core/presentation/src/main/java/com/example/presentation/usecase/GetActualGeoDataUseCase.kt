package com.example.presentation.usecase

import com.example.base.model.geo.ActualGeoLocation

interface GetActualGeoDataUseCase {
    suspend fun invoke(geocodeString:String): Result<ActualGeoLocation>
}