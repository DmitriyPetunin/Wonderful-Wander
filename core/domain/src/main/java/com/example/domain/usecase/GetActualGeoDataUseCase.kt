package com.example.domain.usecase

import com.example.base.model.geo.ActualGeoLocation
import com.example.domain.repository.GeoRepository
import com.example.presentation.usecase.GetActualGeoDataUseCase
import javax.inject.Inject

class GetActualGeoDataUseCaseImpl @Inject constructor(
    private val geoRepository: GeoRepository
): GetActualGeoDataUseCase {
    override suspend fun invoke(geocodeString:String): ActualGeoLocation {
        return geoRepository.getActualGeoData(geocodeString)
    }
}