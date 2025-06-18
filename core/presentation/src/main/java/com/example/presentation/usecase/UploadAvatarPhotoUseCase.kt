package com.example.presentation.usecase

import android.net.Uri

interface UploadAvatarPhotoUseCase {
    suspend fun invoke(uri: Uri): Result<Unit>
}