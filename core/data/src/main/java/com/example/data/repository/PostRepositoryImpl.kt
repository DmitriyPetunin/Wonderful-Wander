package com.example.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.base.model.SavePhotoResult
import com.example.domain.repository.PostRepository
import com.example.network.model.error.ExampleErrorResponse
import com.example.network.service.post.PostService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postService: PostService
):PostRepository,BaseRepo() {
    override suspend fun uploadImage(uri: Uri): Result<SavePhotoResult> {
        return try {
            val photo = createMultipartBody(uri,context)

            val response = postService.uploadImage(photo)
            when{
                response.isSuccessful -> {
                    response.body()?.let {
                        Result.success(SavePhotoResult(fileName = it.fileName))
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

    private fun createMultipartBody(uri: Uri, context:Context): MultipartBody.Part?{
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
                MultipartBody.Part.createFormData("photo", file.name, requestBody)
            }
        } catch (e: Exception) {
            null
        }
    }
}