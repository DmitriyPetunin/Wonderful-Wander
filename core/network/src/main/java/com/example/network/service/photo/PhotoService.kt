package com.example.network.service.photo

import com.example.network.model.photo.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PhotoService {
    @Multipart
    @POST("/api/photos/post")
    suspend fun uploadPostPhoto(
        @Part photo: MultipartBody.Part?
    ): Response<UploadImageResponse>


    @Multipart
    @POST("/api/photos/walk")
    suspend fun uploadWalkPhoto(
        @Query("walkId") walkId:String,
        @Part photo: MultipartBody.Part?
    ): Response<UploadImageResponse>

    @Multipart
    @POST("/api/photos/avatar")
    suspend fun uploadAvatarPhoto(
        @Part photo: MultipartBody.Part?
    ): Response<Unit>
}