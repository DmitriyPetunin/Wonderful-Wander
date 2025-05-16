package com.example.network.service.user

import com.example.network.model.user.follows.SubscribeUserResponse
import com.example.network.model.user.friends.FriendApiResponse
import com.example.network.model.user.profile.res.GetProfileResponse
import com.example.network.model.user.profile.req.UpdateProfileRequestParam
import com.example.network.model.user.profile.res.DeleteProfileResponse
import com.example.network.model.user.profile.res.UpdateProfileResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface UserService {


    @GET("/api/users/me")
    suspend fun getProfile(): GetProfileResponse

    @GET("/api/users/{userId}/friends")
    suspend fun getFriends(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Path("userId") id: String
    ): FriendApiResponse


    @PUT("/api/users/me")
    suspend fun updateProfile(
        input: UpdateProfileRequestParam
    ): UpdateProfileResponse

    @DELETE("/api/users/me")
    suspend fun deleteProfile(): DeleteProfileResponse


    @POST("/api/users/me/follows/{targetUserId}")
    suspend fun followUser(
        @Path("targetUserId") id:String
    ): SubscribeUserResponse
}