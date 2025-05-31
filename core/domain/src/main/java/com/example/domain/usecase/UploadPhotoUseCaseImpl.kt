package com.example.domain.usecase

import android.net.Uri
import com.example.base.model.SavePhotoResult
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.UploadPhotoUseCase
import javax.inject.Inject

class UploadPhotoUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
): UploadPhotoUseCase {
    override suspend fun invoke(uri: Uri): Result<SavePhotoResult> {
        return postRepository.uploadImage(uri)
    }
}