package com.example.data.repository

import com.example.base.model.geo.ActualGeoLocation
import com.example.domain.repository.GeoRepository
import com.example.network.service.geo.GeoService
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoService: GeoService
): GeoRepository {

    override suspend fun getActualGeoData(geocodeData:String): Result<ActualGeoLocation> {
        return try {
            val response = geoService.fetchGeoData(geocode = geocodeData)

            if (response.isSuccessful) {
                val listFeature = response.body()?.response?.GeoObjectCollection?.featureMember

                Result.success(listFeature?.let {
                    if (it.isNotEmpty()) {
                        ActualGeoLocation(text = it[0].GeoObject.metaDataProperty.GeocoderMetaData.text)
                    } else {
                        ActualGeoLocation("")
                    }
                }?: ActualGeoLocation(""))
            } else {
                Result.failure(Exception("Failed with code ${response.code()}"))
            }
        } catch (e:Exception){
            Result.failure(e)
        }
    }
}