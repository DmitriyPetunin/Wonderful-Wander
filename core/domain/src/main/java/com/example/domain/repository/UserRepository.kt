package com.example.domain.repository

import com.example.domain.model.user.People
import com.example.domain.model.user.login.LoginResult
import com.example.domain.model.user.login.LoginUserParam
import com.example.domain.model.user.profile.PersonProfileInfoResult
import com.example.domain.model.user.profile.ProfileInfoResult
import com.example.domain.model.user.profile.UpdateProfileParam
import com.example.domain.model.user.profile.UpdateProfileResult
import com.example.domain.model.user.register.RegisterResult
import com.example.domain.model.user.register.RegisterUserParam


interface UserRepository {

    suspend fun login(inputParam: LoginUserParam): Result<LoginResult>

    suspend fun register(inputParam: RegisterUserParam): Result<RegisterResult>

    suspend fun getAllFriends(page:Int,limit:Int): Result<List<People>>

    suspend fun getAllFollowing(page:Int,limit:Int): Result<List<People>>

    suspend fun getAllFollowers(page:Int,limit:Int): Result<List<People>>

    suspend fun getProfileInfo(): Result<ProfileInfoResult>

    suspend fun updateProfileInfo(inputParam: UpdateProfileParam): Result<UpdateProfileResult>

    suspend fun deleteProfile(): Result<Unit>

    suspend fun getPersonProfileInfoById(id:String):Result<PersonProfileInfoResult>

    suspend fun followToUserById(id:String):Result<Unit>

    suspend fun unFollowToUserById(id:String):Result<Unit>

}