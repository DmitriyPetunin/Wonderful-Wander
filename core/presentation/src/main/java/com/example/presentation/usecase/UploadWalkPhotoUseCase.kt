package com.example.presentation.usecase

import android.net.Uri

interface UploadWalkPhotoUseCase {
    suspend fun invoke(walkId:String,uri: Uri): Result<Unit>
}