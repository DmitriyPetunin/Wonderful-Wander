package com.example.network.service.user

import android.adservices.ondevicepersonalization.RequestToken
import com.example.network.model.user.auth.TokenResponse
import com.example.network.model.user.profile.res.GetProfileResponse
import com.example.network.model.user.profile.req.UpdateProfileRequestParam
import com.example.network.model.user.profile.res.DeleteProfileResponse
import com.example.network.model.user.profile.res.UpdateProfileResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT


interface UserService {


    @GET("/api/users/me")
    suspend fun getProfile(): GetProfileResponse


    @PUT("/api/users/me")
    suspend fun updateProfile(input: UpdateProfileRequestParam): UpdateProfileResponse

    @DELETE("/api/users/me")
    suspend fun deleteProfile(): DeleteProfileResponse

//    @POST("/api/users/me/follows/{targetUserId}")
//    suspend fun followUser(
//        @Path("targetUserId") id:String
//    ):
}