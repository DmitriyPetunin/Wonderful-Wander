package com.android.practise.service

import com.android.practise.entity.GeoObjectCollectionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {
    @GET("v1/")
    suspend fun fetchGeoData(
        @Query("apikey") apiKey: String = "aa702096-3a99-4550-b905-b65777487055",
        @Query("geocode") geocode: String, //"$longitude,$latitude"
        @Query("format") format: String = "json"
    ): GeoObjectCollectionResponse
}