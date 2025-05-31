package com.example.presentation.usecase

import android.net.Uri
import com.example.base.model.SavePhotoResult

interface UploadPhotoUseCase {

    suspend fun invoke(uri: Uri): Result<SavePhotoResult>
}