package com.example.presentation.usecase

import android.net.Uri

interface UploadPostPhotoUseCase {
    suspend fun invoke(uri: Uri): Result<Unit>
}