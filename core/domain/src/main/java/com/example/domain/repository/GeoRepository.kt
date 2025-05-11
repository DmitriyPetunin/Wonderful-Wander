package com.example.domain.repository

import com.example.domain.entity.ActualGeoLocation

interface GeoRepository {

    suspend fun getActualGeoData(geocodeData:String): ActualGeoLocation
}