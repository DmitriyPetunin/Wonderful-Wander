package com.example.domain.usecase

import com.example.base.model.geo.ActualGeoLocationResult
import com.example.domain.repository.GeoRepository
import com.example.presentation.usecase.GetActualGeoDataUseCase
import javax.inject.Inject

class GetActualGeoDataUseCaseImpl(
    private val geoRepository: GeoRepository
): GetActualGeoDataUseCase {
    override suspend fun invoke(geocodeString:String): Result<ActualGeoLocationResult> {
        return geoRepository.getActualGeoData(geocodeString)
    }
}