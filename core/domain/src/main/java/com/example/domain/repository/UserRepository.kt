package com.example.domain.repository

import com.example.base.model.user.login.LoginResult
import com.example.base.model.user.login.LoginUserParam
import com.example.base.model.user.register.RegisterResult
import com.example.base.model.user.register.RegisterUserParam
import com.example.base.model.user.profile.UpdateProfileParam
import com.example.base.model.user.friends.Friend
import com.example.base.model.user.profile.ProfileInfoResult
import com.example.base.model.user.profile.UpdateProfileResult

interface UserRepository {

    suspend fun login(inputParam: LoginUserParam): Result<LoginResult>

    suspend fun register(inputParam: RegisterUserParam): Result<RegisterResult>

    suspend fun getAllFriends(): Result<List<Friend>>

    suspend fun getProfileInfo(): Result<ProfileInfoResult>

    suspend fun updateProfileInfo(inputParam: UpdateProfileParam): Result<UpdateProfileResult>

    suspend fun deleteProfile(): Result<Unit>

}