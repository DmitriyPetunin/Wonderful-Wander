package com.example.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.base.model.SavePhotoResult
import com.example.domain.repository.PostRepository
import com.example.network.service.post.PostService
import dagger.hilt.android.qualifiers.ApplicationContext
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
        val response = safeApiCall { postService.uploadImage(createMultipartBody(uri,context)) }
        return Result.success(SavePhotoResult(photoId = ""))
    }

    private fun createMultipartBody(uri: Uri, context:Context): MultipartBody.Part?{
        return try {
            val mime = context.contentResolver.getType(uri)
            Log.d("URI","mimetype = ${mime}")

            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                // Создаем временный файл
                val file = File.createTempFile("upload", ".jpg", context.cacheDir)
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }

                // Создаем Multipart часть
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }
        } catch (e: Exception) {
            null
        }
    }
}