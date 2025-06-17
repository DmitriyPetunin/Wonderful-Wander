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

}