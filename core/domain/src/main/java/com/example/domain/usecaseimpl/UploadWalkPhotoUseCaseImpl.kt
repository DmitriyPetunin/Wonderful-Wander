package com.example.domain.usecaseimpl

import android.net.Uri
import com.example.domain.repository.PhotoRepository
import com.example.domain.usecase.UploadWalkPhotoUseCase

class UploadWalkPhotoUseCaseImpl(
    private val photoRepository: PhotoRepository
): UploadWalkPhotoUseCase {
    override suspend fun invoke(walkId: String, uri: Uri): Result<Unit> {
        return photoRepository.uploadWalkImage(walkId,uri)
    }
}