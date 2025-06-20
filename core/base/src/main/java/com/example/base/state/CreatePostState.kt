package com.example.base.state

import android.net.Uri
import android.net.Uri.EMPTY
import com.example.base.model.post.category.Category

data class CreatePostState (
    val photoState: PhotoState = PhotoState.Init,
    val photoUri: Uri = EMPTY,
    val status: String = "",
    val title:String = "",
    val queryParam:String = "",
    val selectedCategory:Category? = null,

    val isLoading:Boolean = true,
    val searchBarIsActive:Boolean = false,

    val fileName:String = "",


    val listOfCategories:List<Category> = emptyList()
)

sealed class PhotoState{
    data object Error:PhotoState()
    data object Loading: PhotoState()
    data object Init:PhotoState()
    data object Success:PhotoState()
}
