package com.example.presentation.usecase

import android.net.Uri
import com.example.base.model.SavePhotoResult

interface UploadAvatarPhotoUseCase {
    suspend fun invoke(uri: Uri): Result<Unit>
}