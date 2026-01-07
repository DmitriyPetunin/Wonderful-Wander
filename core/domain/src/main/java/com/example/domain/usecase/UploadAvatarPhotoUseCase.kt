package com.example.domain.usecase

import android.net.Uri

interface UploadAvatarPhotoUseCase {
    suspend fun invoke(uri: Uri): Result<Unit>
}