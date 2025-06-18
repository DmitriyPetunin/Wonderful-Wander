package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.post.PostDetailAction
import com.example.base.model.post.PostResult
import com.example.base.state.PostDetailState
import com.example.base.state.UserData
import com.example.presentation.usecase.GetPostDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostDetailByIdUseCase: GetPostDetailByIdUseCase
): ViewModel(){

    private val _state = MutableStateFlow(PostDetailState())
    val state: StateFlow<PostDetailState> = _state.asStateFlow()


    private val _event = MutableSharedFlow<PostDetailState>()
    val event: SharedFlow<PostDetailState> = _event.asSharedFlow()


    fun onAction(action:PostDetailAction){
        when(action){

            is PostDetailAction.UpdatePostId -> {
                updatePostId(action.id)
                loadDetailInfo()
            }

            PostDetailAction.CommentPost -> {}
            PostDetailAction.LikePost -> {}
            PostDetailAction.NavigateBack -> {}
            PostDetailAction.ShowAllComments -> {}
            is PostDetailAction.UserClicked -> {}
        }

    }

    private fun loadDetailInfo(){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = getPostDetailByIdUseCase.invoke(state.value.postId)

            result.fold(
                onSuccess = { value: PostResult ->
                    _state.update {
                        it.copy(
                            photoUrl = value.photoUrl,
                            user = UserData(userId = value.user.userId,profilePictureUrl = value.user.avatarUrl, username = value.user.userName),
                            title = value.title,
                            categoryName = value.categoryName,
                            createdAt = value.createdAt,
                            commentsCount = value.commentsCount,
                            likesCount = value.likesCount
                        )
                    }
                },
                onFailure = {

                }
            )
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun updatePostId(id:String){
        _state.update {
            it.copy(postId = id)
        }
    }


}