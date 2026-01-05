package com.example.feature.post.impl.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.SessionManager
import com.example.feature.post.api.action.PostDetailAction
import com.example.feature.post.api.event.PostDetailEvent
import com.example.base.model.post.Comment
import com.example.base.model.post.Post
import com.example.base.state.UserData
import com.example.feature.post.api.state.CommentUi
import com.example.feature.post.api.state.PostDetailState
import com.example.domain.usecase.DeleteCommentUseCase
import com.example.domain.usecase.GetAllCommentsByPostIdUseCase
import com.example.domain.usecase.GetPostDetailByIdUseCase
import com.example.domain.usecase.LikePostUseCase
import com.google.firebase.analytics.FirebaseAnalytics
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
    private val likePostUseCase: LikePostUseCase,
    private val sessionManager: SessionManager,
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    init {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "post_detail_screen")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

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
                viewModelScope.launch {
                    _event.emit(PostDetailEvent.NavigateToPersonProfile(action.userId))
                }
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
            val result = deleteCommentUseCase.invoke(postId = state.value.postId, commentId = commentId)

            result.fold(
                onSuccess = {
                    _event.emit(PostDetailEvent.DeleteComment(message = "коммент успешно удалён"))
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

