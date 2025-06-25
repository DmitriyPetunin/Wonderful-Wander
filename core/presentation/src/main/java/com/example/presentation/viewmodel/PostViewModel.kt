package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.SessionManager
import com.example.base.action.post.PostDetailAction
import com.example.base.event.post.PostDetailEvent
import com.example.base.model.post.Comment
import com.example.base.model.post.Post
import com.example.base.model.user.People
import com.example.base.state.CommentUi
import com.example.base.state.PeopleEnum
import com.example.base.state.PostDetailState
import com.example.base.state.UserData
import com.example.presentation.usecase.DeleteCommentUseCase
import com.example.presentation.usecase.GetAllCommentsByPostIdUseCase
import com.example.presentation.usecase.GetPostDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val getPostDetailByIdUseCase: GetPostDetailByIdUseCase,
    private val getAllCommentsByPostIdUseCase: GetAllCommentsByPostIdUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(PostDetailState())
    val state: StateFlow<PostDetailState> = _state.asStateFlow()


    private val _event = MutableSharedFlow<PostDetailEvent>()
    val event: SharedFlow<PostDetailEvent> = _event.asSharedFlow()


    fun onAction(action: PostDetailAction) {
        when (action) {

            is PostDetailAction.UpdatePostId -> {
                updatePostId(action.id)
                getDetailInfo()
            }

            PostDetailAction.CommentPost -> {}
            PostDetailAction.LikePost -> {}
            PostDetailAction.NavigateBack -> {}
            PostDetailAction.ShowAllComments -> {
                updateVisibleComments()
                getAllComments()
            }

            is PostDetailAction.UserClicked -> {
//                viewModelScope.launch {
//                    _event.emit()
//                }
            }

            PostDetailAction.LoadMoreComments -> TODO()
            is PostDetailAction.DeleteComment -> {
                deleteComment(action.id)
            }

            is PostDetailAction.ClickOnComment -> {
                clickOnComment(action.id)
            }
        }

    }

    private fun getDetailInfo() {
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = getPostDetailByIdUseCase.invoke(state.value.postId)

            result.fold(
                onSuccess = { value: Post ->
                    _state.update {
                        it.copy(
                            photoUrl = value.photoUrl,
                            user = UserData(
                                userId = value.user.userId,
                                profilePictureUrl = value.user.avatarUrl,
                                username = value.user.userName
                            ),
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

    private fun getAllComments() {
        Log.d("STATE", "currentPage = ${state.value.currentPage}")
        if ((!state.value.isInitialLoadingComments && state.value.isLoading) || state.value.endReached) return

        _state.update { it.copy(isLoadingComments = true) }

        viewModelScope.launch {
            delay(1000L)
            val result = getAllCommentsByPostIdUseCase.invoke(
                postId = state.value.postId,
                page = state.value.currentPage,
                limit = state.value.limit
            )

            _state.update { currentState ->
                result.fold(
                    onSuccess = { newComments: List<Comment> ->
                        if (!state.value.isInitialLoadingComments && newComments.lastOrNull() == state.value.listOfComments.map { it.comment }.lastOrNull()) {
                            currentState.copy(
                                isLoadingComments = false,
                                endReached = true
                            )
                        } else {
                            currentState.copy(
                                listOfComments = currentState.listOfComments + getListOfCommentsUI(newComments),
                                isLoadingComments = false,
                                currentPage = currentState.currentPage + 1,
                                endReached = newComments.size < currentState.limit
                            )
                        }
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState.copy(isLoadingComments = false)
                    }
                )
            }
        }
    }

    private fun deleteComment(commentId: String) {
        viewModelScope.launch {
            val result =
                deleteCommentUseCase.invoke(postId = state.value.postId, commentId = commentId)

            result.fold(
                onSuccess = {
                    _event.emit(PostDetailEvent.DeleteComment(message = ""))
                },
                onFailure = { exception ->
                    exception.message?.let {
                        _event.emit(PostDetailEvent.DeleteComment(message = it))
                    }
                }
            )
        }
    }
    private fun clickOnComment(commentId: String){
        viewModelScope.launch {
            //val result = getCommentByIdUseCase
        }
    }


    private fun updateVisibleComments() {
        _state.update {
            it.copy(commentsIsVisible = true)
        }
    }

    private fun updatePostId(id: String) {
        _state.update {
            it.copy(postId = id)
        }
    }

    private fun getListOfCommentsUI(newComments:List<Comment>):List<CommentUi>{
        return newComments.map { comment: Comment ->
            CommentUi(comment, canDelete = comment.user.userId == sessionManager.getUserId())
        }
    }
}