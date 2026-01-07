package com.example.domain.usecaseimpl

import android.net.Uri
import com.example.domain.repository.PhotoRepository
import com.example.domain.usecase.UploadAvatarPhotoUseCase

class UploadAvatarPhotoUseCaseImpl (
    private val photoRepository: PhotoRepository
): UploadAvatarPhotoUseCase {
    override suspend fun invoke(uri: Uri): Result<Unit> {
        return photoRepository.uploadAvatarImage(uri)
    }
}