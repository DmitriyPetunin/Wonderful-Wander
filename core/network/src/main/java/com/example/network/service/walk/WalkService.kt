package com.example.network.service.walk

import com.example.network.model.walk.req.CreateWalkRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WalkService {

    @POST("/api/walks")
    suspend fun createWalk(
        @Body data: CreateWalkRequest
    ):Response<Unit>
}