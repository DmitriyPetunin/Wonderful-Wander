package com.example.data.repository

import android.util.Log
import com.example.base.model.user.login.LoginResult
import com.example.base.model.user.login.LoginUserParam
import com.example.base.model.user.register.RegisterResult
import com.example.base.model.user.register.RegisterUserParam
import com.example.base.model.user.People
import com.example.domain.repository.UserRepository
import com.example.base.SessionManager
import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility
import com.example.base.model.user.profile.PersonProfileInfoResult
import com.example.base.model.user.profile.ProfileInfoResult
import com.example.base.model.user.profile.UpdateProfileParam
import com.example.base.model.user.profile.UpdateProfileResult
import com.example.data.mapper.FriendApiToFriendDomainMapper
import com.example.data.mapper.PersonProfileInfoResponseToPersonProfileInfoResultMapper
import com.example.data.mapper.ProfileResponseToProfileInfoMapper
import com.example.network.model.error.ExampleErrorResponse
import com.example.network.model.user.login.req.LoginRequest
import com.example.network.model.user.profile.req.UpdateProfileRequest
import com.example.network.model.user.register.req.RegisterUserRequest
import com.example.network.service.auth.AuthService
import com.example.network.service.user.UserService
import kotlinx.serialization.json.Json
import javax.inject.Inject
import kotlin.math.min

class UserRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    private val sessionManager: SessionManager,
    private val profileInfoMapper: ProfileResponseToProfileInfoMapper,
    private val friendApiToFriendDomainMapper: FriendApiToFriendDomainMapper,
    private val personProfileInfoResponseToPersonProfileInfoResultMapper: PersonProfileInfoResponseToPersonProfileInfoResultMapper
) : UserRepository {

    override suspend fun login(inputParam: LoginUserParam): Result<LoginResult> {
        return try {
            val response = authService.login(LoginRequest(username = inputParam.email, password = inputParam.password))

            if (response.isSuccessful) {
                response.body()?.let {
                    it.accessToken?.let { sessionManager.saveAccessToken(it) }
                    it.refreshToken?.let { sessionManager.saveRefreshToken(it) }
                }
                Log.d("AccessToken", sessionManager.getAccessToken())
                Result.success(LoginResult(status = "success"))
            } else {
                Result.failure(Exception("Failed to delete profile: ${response.code()}"))
            }
        } catch (e: Exception) {
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

            if (response.isSuccessful) {
                Result.success(RegisterResult(status = "success"))
            } else {
                Result.failure(Exception("Failed register : ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProfileInfo(): Result<ProfileInfoResult> {
        return try {
            val response = userService.getProfile()

            if (response.isSuccessful) {
                response.body()?.userId?.let {
                    Log.d("USERID",it)
                    sessionManager.saveUserId(it)
                }
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
        } catch (e: Exception) {
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


    override suspend fun getAllFriends(page:Int,limit:Int): Result<List<People>> {
        val userId = sessionManager.getUserId()

        return try {
            val response = userService.getFriends(id = userId, page = page, size = limit)

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

    override suspend fun getAllFollowing(page:Int,limit:Int): Result<List<People>> {
        val userId = sessionManager.getUserId()

        return try {
            val response = userService.getFollowing(id = userId, page = page, size = limit)

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

    override suspend fun getAllFollowers(page:Int,limit:Int): Result<List<People>> {
        val userId = sessionManager.getUserId()

        return try {
            val response = userService.getFollowers(id = userId, page = page, size = limit)

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

    override suspend fun getPersonProfileInfoById(id: String): Result<PersonProfileInfoResult> {
        return try {
            val response = userService.getProfileByUserId(id)
            when {
                response.isSuccessful -> {
                    response.body()?.let { successBody ->
                        Result.success(
                            personProfileInfoResponseToPersonProfileInfoResultMapper.invoke(successBody)
                        )
                    } ?: Result.failure(Exception("Empty response body"))
                }
                response.code() == 401 -> {
                    val errorBody = try {
                        response.errorBody()?.string()?.let {
                            Json.decodeFromString<ExampleErrorResponse>(it)
                        }
                    } catch (e: Exception) {
                        null
                    }
                    Result.failure(
                        Exception(errorBody?.message ?: "Don`t Auth: ${response.code()}")
                    )
                }
                else -> {
                    Result.failure(Exception("Server error: ${response.code()}"))
                }
            }
        } catch (e:Exception){
            Log.d("TEST-TAG", "Error get PersonProfile Info By Id= ${id}")
            Result.failure(e)
        }
    }

    override suspend fun followToUserById(id: String): Result<Unit> {

        return try {
            val response = userService.followToUserById(id = id)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
//                response.errorBody() as CustomErrorBody
                Result.failure(Exception("Failed to follow profile: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.d("TEST-TAG", "Error while deleting profile")
            Result.failure(e)
        }

    }

    override suspend fun unFollowToUserById(id: String): Result<Unit> {

        return try {
            val response = userService.unFollowToUserById(id = id)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
//                response.errorBody() as CustomErrorBody
                Result.failure(Exception("Failed to follow profile: ${response.code()}"))
            }
        } catch (e: Exception) {
            Log.d("TEST-TAG", "Error while deleting profile")
            Result.failure(e)
        }
    }
}