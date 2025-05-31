package com.example.domain.repository

import android.net.Uri
import com.example.base.model.SavePhotoResult

interface PostRepository {
    suspend fun uploadImage(uri: Uri): Result<SavePhotoResult>
}