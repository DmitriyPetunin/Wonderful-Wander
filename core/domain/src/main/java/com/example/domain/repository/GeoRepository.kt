package com.example.domain.repository

import com.example.base.model.geo.ActualGeoLocationResult


interface GeoRepository {

    suspend fun getActualGeoData(geocodeData:String): Result<ActualGeoLocationResult>
}