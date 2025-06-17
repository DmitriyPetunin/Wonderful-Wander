package com.example.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.base.model.SavePhotoResult
import com.example.domain.repository.PhotoRepository
import com.example.network.model.error.ExampleErrorResponse
import com.example.network.service.photo.PhotoService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val photoService: PhotoService
): PhotoRepository {
    override suspend fun uploadWalkImage(walkId: String, uri: Uri): Result<Unit> {
        return try {
            val photo = createMultipartBody(uri,context, name = "walk")

            val response = photoService.uploadWalkPhoto(walkId = walkId,photo = photo)
            when{
                response.isSuccessful -> {
                    response.body()?.let {
                        Result.success(Unit)
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
                else -> {Result.failure(Exception("Server error: ${response.code()}"))}
            }
        } catch (e:Exception){
            Log.d("TEST-TAG", "Error upload Photo")
            Result.failure(e)
        }
    }

    override suspend fun uploadPostImage(uri: Uri): Result<Unit> {
        return try {
            val photo = createMultipartBody(uri,context,name = "photo")

            val response = photoService.uploadPostPhoto(photo)
            when{
                response.isSuccessful -> {
                    response.body()?.let {
                        Result.success(Unit)
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
                else -> {Result.failure(Exception("Server error: ${response.code()}"))}
            }
        } catch (e:Exception){
            Log.d("TEST-TAG", "Error upload Photo")
            Result.failure(e)
        }
    }

    override suspend fun uploadAvatarImage(uri: Uri): Result<Unit> {
        return try {
            val photo = createMultipartBody(uri,context,name = "avatar")

            val response = photoService.uploadAvatarPhoto(photo)
            when{
                response.isSuccessful -> {
                    Result.success(Unit)
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
            Log.d("TEST-TAG", "Error upload Photo")
            Result.failure(e)
        }
    }


    private fun createMultipartBody(uri: Uri, context: Context,name:String): MultipartBody.Part?{
        return try {
            val mime = context.contentResolver.getType(uri)
            Log.d("URI","mimetype = ${mime}")

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val file = File.createTempFile("upload", ".jpg", context.cacheDir)
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                // Создаем Multipart часть
                val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(name, file.name, requestBody)
            }
        } catch (e: Exception) {
            null
        }
    }
}