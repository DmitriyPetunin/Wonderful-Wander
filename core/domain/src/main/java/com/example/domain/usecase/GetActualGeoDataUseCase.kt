package com.example.domain.usecase

import com.example.domain.entity.ActualGeoLocation
import com.example.domain.repository.GeoRepository
import javax.inject.Inject


interface GetActualGeoDataUseCase {
    suspend fun invoke(geocodeString:String): ActualGeoLocation
}


class GetActualGeoDataUseCaseImpl @Inject constructor(
    private val geoRepository: GeoRepository
): GetActualGeoDataUseCase {
    override suspend fun invoke(geocodeString:String): ActualGeoLocation {
        return geoRepository.getActualGeoData(geocodeString)
    }
}