package com.example.base.action.post

import android.net.Uri

sealed class CreatePostAction {

    data class UpdatePhotoUri(val input:Uri): CreatePostAction()

    data object SubmitToAddPhoto : CreatePostAction()
}