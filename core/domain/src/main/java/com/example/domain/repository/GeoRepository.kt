package com.example.domain.repository

import com.example.domain.model.geo.ActualGeoLocationResult


interface GeoRepository {

    suspend fun getActualGeoData(geocodeData:String): Result<ActualGeoLocationResult>
}