package com.example.domain.usecase

import android.net.Uri
import com.example.domain.repository.PhotoRepository
import com.example.presentation.usecase.UploadAvatarPhotoUseCase
import javax.inject.Inject

class UploadAvatarPhotoUseCaseImpl (
    private val photoRepository: PhotoRepository
): UploadAvatarPhotoUseCase {
    override suspend fun invoke(uri: Uri): Result<Unit> {
        return photoRepository.uploadAvatarImage(uri)
    }
}