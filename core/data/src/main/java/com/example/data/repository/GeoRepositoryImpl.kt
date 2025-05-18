package com.example.data.repository

import com.example.base.model.geo.ActualGeoLocation
import com.example.domain.repository.GeoRepository
import com.example.network.service.geo.GeoService
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoService: GeoService
): GeoRepository {

    override suspend fun getActualGeoData(geocodeData:String): ActualGeoLocation {
        val response = geoService.fetchGeoData(geocode = geocodeData)

        val listFeature = response.response.GeoObjectCollection.featureMember


        var text = ""
        if(listFeature.isNotEmpty()){
            text = listFeature[0].GeoObject.metaDataProperty.GeocoderMetaData.text
        }
        return ActualGeoLocation(text = text)
    }
}