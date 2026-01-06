package com.example.domain.usecaseimpl

import com.example.domain.model.geo.ActualGeoLocationResult
import com.example.domain.repository.GeoRepository
import com.example.domain.usecase.GetActualGeoDataUseCase

class GetActualGeoDataUseCaseImpl(
    private val geoRepository: GeoRepository
): GetActualGeoDataUseCase {
    override suspend fun invoke(geocodeString:String): Result<ActualGeoLocationResult> {
        return geoRepository.getActualGeoData(geocodeString)
    }
}