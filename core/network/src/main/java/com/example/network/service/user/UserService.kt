package com.example.network.service.user

import com.example.network.model.user.people.SubscribeUserResponse
import com.example.network.model.user.friends.PeopleApiResponse
import com.example.network.model.user.people.GetPersonProfileInfo
import com.example.network.model.user.profile.res.GetProfileResponse
import com.example.network.model.user.profile.req.UpdateProfileRequest
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
    suspend fun getProfile(): Response<GetProfileResponse>



    @PUT("/api/users/me")
    suspend fun updateProfile(
        input: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @DELETE("/api/users/me")
    suspend fun deleteProfile(): Response<DeleteProfileResponse?>


    @GET("/api/users/{userId}/friends")
    suspend fun getFriends(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Path("userId") id: String
    ): Response<PeopleApiResponse>

    @GET("/api/users/{userId}/following")
    suspend fun getFollowing(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Path("userId") id: String
    ): Response<PeopleApiResponse>

    @GET("/api/users/{userId}/followers")
    suspend fun getFollowers(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Path("userId") id: String
    ): Response<PeopleApiResponse>

    @POST("/api/users/me/follows/{targetUserId}")
    suspend fun followToUserById(
        @Path("targetUserId") id:String
    ): Response<SubscribeUserResponse>

    @DELETE("/api/users/me/follows/{targetUserId}")
    suspend fun unFollowToUserById(
        @Path("targetUserId") id:String
    ): Response<SubscribeUserResponse>

    @GET("/api/users/{targetUserId}")
    suspend fun getProfileByUserId(
        @Path("targetUserId") id:String
    ):Response<GetPersonProfileInfo>

}