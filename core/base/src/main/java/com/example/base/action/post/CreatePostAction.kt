package com.example.base.action.post

import android.net.Uri
import com.example.base.model.post.category.Category

sealed class CreatePostAction {

    data object Init:CreatePostAction()

    data class UpdatePhotoUri(val input:Uri): CreatePostAction()

    data class UpdateTitle(val input:String):CreatePostAction()

    data class UpdateQueryParam(val input:String):CreatePostAction()
    data class UpdateSelectedCategory(val category: Category?) : CreatePostAction()

    data class UpdateActiveParam(val active:Boolean):CreatePostAction()


}