package com.example.data.repository

import android.util.Log
import com.example.base.model.user.login.LoginResult
import com.example.base.model.user.login.LoginUserParam
import com.example.base.model.user.register.RegisterResult
import com.example.base.model.user.register.RegisterUserParam
import com.example.base.model.user.friends.Friend
import com.example.domain.repository.UserRepository
import com.example.base.SessionManager
import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility
import com.example.base.model.user.profile.ProfileInfoResult
import com.example.base.model.user.profile.UpdateProfileParam
import com.example.base.model.user.profile.UpdateProfileResult
import com.example.data.mapper.FriendApiToFriendDomainMapper
import com.example.data.mapper.ProfileResponseToProfileInfoMapper
import com.example.network.model.user.login.req.LoginRequest
import com.example.network.model.user.profile.req.UpdateProfileRequest
import com.example.network.model.user.register.req.RegisterUserRequest
import com.example.network.service.auth.AuthService
import com.example.network.service.user.UserService
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    private val sessionManager: SessionManager,
    private val profileInfoMapper: ProfileResponseToProfileInfoMapper,
    private val friendApiToFriendDomainMapper: FriendApiToFriendDomainMapper
): UserRepository {

    override suspend fun login(inputParam: LoginUserParam): Result<LoginResult> {
        return try {
            val response = authService.login(LoginRequest(inputParam.email,inputParam.password))

            if(response.isSuccessful){
                response.body()?.let {
                    it.accessToken?.let { sessionManager.saveAccessToken(it) }
                    it.refreshToken?.let { sessionManager.saveRefreshToken(it) }
                }
                Result.success(LoginResult(status = "success"))
            } else {
                Result.failure(Exception("Failed to delete profile: ${response.code()}"))
            }
        } catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun register(inputParam: RegisterUserParam): Result<RegisterResult> {
        return try {
            val response = authService.register(
                RegisterUserRequest(
                    username = inputParam.username,
                    password = inputParam.password,
                    cpassword = inputParam.confirmPassword,
                    email = inputParam.email,
                    firstname = inputParam.firstName,
                    lastname = inputParam.lastName,
                )
            )

            if(response.isSuccessful){
                Result.success(RegisterResult(status = "success"))
            } else{
                Result.failure(Exception("Failed register : ${response.code()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProfileInfo(): Result<ProfileInfoResult> {
        return try {
            val response = userService.getProfile()

            if (response.isSuccessful){
                response.body()?.userId?.let { sessionManager.saveUserId(it) }
                Result.success(profileInfoMapper.invoke(response.body()))
            } else {
                Result.failure(Exception("Failed to delete profile: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfileInfo(inputParam: UpdateProfileParam): Result<UpdateProfileResult> {

        return try {
            val response = userService.updateProfile(
                UpdateProfileRequest(
                    email = inputParam.email,
                    firstname = inputParam.firstName,
                    lastname = inputParam.lastName,
                    bio = inputParam.bio,
                    photoVisibility = PhotosVisibility.toStringValue(inputParam.photoVisibility),
                    walkVisibility = WalkVisibility.toStringValue(inputParam.walkVisibility)
                )
            )

            if (response.isSuccessful) {
                Result.success(UpdateProfileResult(status = "success"))
            } else {
                Result.failure(Exception("Failed update profile info: ${response.code()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(): Result<Unit> {
        return try {
            val response = userService.deleteProfile()

            if (response.isSuccessful) {
                sessionManager.clearSession()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to delete profile: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.d("TEST-TAG", "Error while deleting profile")
            Result.failure(e)
        }
    }


    override suspend fun getAllFriends(): Result<List<Friend>> {
        val userId = sessionManager.getUserId()

        return try {
            val response = userService.getFriends(id = userId)

            if (response.isSuccessful){
                Result.success(response.body()?.listOfFriends?.map {
                    friendApiToFriendDomainMapper.invoke(it)
                }?: emptyList()
                )
            } else{
                Result.failure(Exception("Failed to get friends: ${response.code()}"))
            }


        } catch (e:Exception){
            Log.d("TEST-TAG", "Error get friends for userId = ${userId}")
            Result.failure(e)
        }
    }
}