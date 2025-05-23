package com.example.data.repository

import com.example.base.model.geo.ActualGeoLocationResult
import com.example.domain.repository.GeoRepository
import com.example.network.service.geo.GeoService
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoService: GeoService
): GeoRepository {

    override suspend fun getActualGeoData(geocodeData:String): Result<ActualGeoLocationResult> {
        return try {
            val response = geoService.fetchGeoData(geocode = geocodeData)

            if (response.isSuccessful) {
                val listFeature = response.body()?.response?.GeoObjectCollection?.featureMember

                Result.success(listFeature?.let {
                    if (it.isNotEmpty()) {
                        ActualGeoLocationResult(text = it[0].GeoObject.metaDataProperty.GeocoderMetaData.text)
                    } else {
                        ActualGeoLocationResult("")
                    }
                }?: ActualGeoLocationResult(""))
            } else {
                Result.failure(Exception("Failed with code ${response.code()}"))
            }
        } catch (e:Exception){
            Result.failure(e)
        }
    }
}