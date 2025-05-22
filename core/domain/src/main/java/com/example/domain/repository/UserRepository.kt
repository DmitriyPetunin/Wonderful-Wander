package com.example.domain.repository

import com.example.base.model.user.LoginResponse
import com.example.base.model.user.LoginUserParam
import com.example.base.model.user.ProfileInfo
import com.example.base.model.user.RegisterResponse
import com.example.base.model.user.RegisterUserParam
import com.example.base.model.user.UpdateProfileParam
import com.example.base.model.user.friends.Friend
import com.example.base.model.user.friends.UpdateProfileResult

interface UserRepository {

    suspend fun login(inputParam:LoginUserParam): Result<LoginResponse>

    suspend fun register(inputParam: RegisterUserParam): Result<RegisterResponse>

    suspend fun getAllFriends(): Result<List<Friend>>

    suspend fun getProfileInfo(): Result<ProfileInfo>

    suspend fun updateProfileInfo(inputParam: UpdateProfileParam): Result<UpdateProfileResult>

    suspend fun deleteProfile(): Result<Unit>

}