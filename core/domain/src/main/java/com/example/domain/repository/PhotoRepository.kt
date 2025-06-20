package com.example.domain.repository

import android.net.Uri

interface PhotoRepository {
    suspend fun uploadWalkImage(walkId:String, uri: Uri): Result<Unit>
    suspend fun uploadPostImage(uri: Uri): Result<String>
    suspend fun uploadAvatarImage(uri: Uri):Result<Unit>
}