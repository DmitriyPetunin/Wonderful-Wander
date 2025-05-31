package com.example.network.service.post

import com.example.network.model.photo.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PostService {

    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part?
    ): Response<UploadImageResponse>
}