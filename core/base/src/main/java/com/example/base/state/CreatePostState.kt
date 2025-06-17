package com.example.base.state

import android.net.Uri
import android.net.Uri.EMPTY

data class CreatePostState (
    val photoState: PhotoState = PhotoState.Init,
    val photoUri: Uri = EMPTY,
    val status: String = ""
)

sealed class PhotoState{

    data object Loading: PhotoState()
    data object Init:PhotoState()
    data object Success:PhotoState()
}
