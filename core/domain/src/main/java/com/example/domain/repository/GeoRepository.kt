package com.example.domain.repository

import com.example.base.model.geo.ActualGeoLocation

interface GeoRepository {

    suspend fun getActualGeoData(geocodeData:String): ActualGeoLocation
}