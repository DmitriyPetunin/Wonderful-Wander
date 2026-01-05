package com.example.domain.usecaseimpl

import android.net.Uri
import com.example.domain.repository.PhotoRepository
import com.example.domain.usecase.UploadPostPhotoUseCase

class UploadPostPhotoUseCaseImpl(
    private val photoRepository: PhotoRepository
): UploadPostPhotoUseCase {
    override suspend fun invoke(uri: Uri): Result<String> {
        return photoRepository.uploadPostImage(uri)
    }
}