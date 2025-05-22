package com.example.network.service.user

import com.example.network.model.user.follows.SubscribeUserResponse
import com.example.network.model.user.friends.FriendApiResponse
import com.example.network.model.user.login.req.LoginRequestParam
import com.example.network.model.user.login.res.LoginResponse
import com.example.network.model.user.profile.res.GetProfileResponse
import com.example.network.model.user.profile.req.UpdateProfileRequestParam
import com.example.network.model.user.profile.res.DeleteProfileResponse
import com.example.network.model.user.profile.res.UpdateProfileResponse
import com.example.network.model.user.register.req.RegisterUserRequestParam
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface UserService {

    @GET("/api/auth/login")
    suspend fun login(input: LoginRequestParam): Response<LoginResponse>

    @GET("/api/auth/register")
    suspend fun register(input: RegisterUserRequestParam): Response<LoginResponse>

    @GET("/api/users/me")
    suspend fun getProfile(): Response<GetProfileResponse>

    @GET("/api/users/{userId}/friends")
    suspend fun getFriends(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Path("userId") id: String
    ): Response<FriendApiResponse>


    @PUT("/api/users/me")
    suspend fun updateProfile(
        input: UpdateProfileRequestParam
    ): Response<UpdateProfileResponse>

    @DELETE("/api/users/me")
    suspend fun deleteProfile(): Response<DeleteProfileResponse?>


    @POST("/api/users/me/follows/{targetUserId}")
    suspend fun followUser(
        @Path("targetUserId") id:String
    ): Response<SubscribeUserResponse>
}