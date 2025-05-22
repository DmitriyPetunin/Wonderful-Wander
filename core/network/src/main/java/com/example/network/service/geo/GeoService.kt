package com.example.network.service.geo


import com.example.network.model.geo.GeoObjectCollectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {
    @GET("v1/")
    suspend fun fetchGeoData(
        @Query("geocode") geocode: String, //"$longitude,$latitude"
    ): Response<GeoObjectCollectionResponse>
}