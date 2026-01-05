package com.example.feature.post.impl.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.post.api.action.PostsAction
import com.example.feature.post.api.event.PostsEvent
import com.example.base.model.post.CommentCreateParam
import com.example.base.model.post.Post
import com.example.feature.post.api.state.PostsState
import com.example.domain.usecase.CreateCommentUseCase
import com.example.domain.usecase.GetRecommendedPostsUseCase
import com.example.domain.usecase.LikePostUseCase
import com.example.domain.usecase.SavePostByPostIdUseCase
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
class PostsViewModel @Inject constructor(
    private val getRecommendedPostsUseCase: GetRecommendedPostsUseCase,
    private val savePostByPostIdUseCase: SavePostByPostIdUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val firebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    init {
        val bundle = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "posts_screen")
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    private val _state = MutableStateFlow(PostsState())
    val state: StateFlow<PostsState> = _state.asStateFlow()


    private val _event = MutableSharedFlow<PostsEvent>()
    val event: SharedFlow<PostsEvent> = _event.asSharedFlow()

    fun onAction(action: PostsAction) {
        when (action) {
            is PostsAction.SubmitCreatePost -> {
                viewModelScope.launch {
                    _event.emit(PostsEvent.NavigateToCreatePost)
                }
            }

            is PostsAction.UpdateBottomSheetVisible -> {
                updateBottomSheetState()
                resetPostId()
            }

            is PostsAction.LoadMorePosts -> {
                loadRecommendedPosts()
            }

            is PostsAction.SubmitPostItem -> {
                viewModelScope.launch {
                    _event.emit(PostsEvent.NavigateToDetailPost(action.postId))
                }
            }

            is PostsAction.SubmitSavePost -> {
                savePost(action.postId)
            }

            is PostsAction.UpdateCommentMessage -> {
                updateCommentMessage(action.message)
            }

            is PostsAction.SuccessWritingCommentForPost -> {
                createComment()
                updateBottomSheetState()
            }

            is PostsAction.FastCommentClick -> {
                updatePostId(action.postId)
                updateBottomSheetState()
            }

            is PostsAction.SubmitLikePost -> {
                likePost(action.postId)
            }
        }
    }


    private fun loadRecommendedPosts() {
        if ((!state.value.isInitialLoadingPosts && state.value.isLoading) || state.value.endReachedPosts) return
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(1000L)
            val result = getRecommendedPostsUseCase.invoke(
                page = state.value.currentPagePosts,
                limit = state.value.limit
            )

            _state.update { currentState ->
                result.fold(
                    onSuccess = { newPosts: List<Post> ->
                        if (!state.value.isInitialLoadingPosts && newPosts.lastOrNull() == state.value.listOfPosts.lastOrNull()) {
                            currentState.copy(
                                isLoading = false,
                                endReachedPosts = true
                            )
                        } else {
                            currentState.copy(
                                listOfPosts = currentState.listOfPosts + newPosts,
                                isLoading = false,
                                currentPagePosts = currentState.currentPagePosts + 1,
                                endReachedPosts = newPosts.size < currentState.limit
                            )
                        }
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState.copy(isLoading = false)
                    }
                )
            }
        }
    }

    private fun createComment() {
        viewModelScope.launch {
            val result = createCommentUseCase.invoke(
                postId = state.value.postId,
                data = CommentCreateParam(state.value.commentText, null)
            )
            result.fold(
                onSuccess = {
                    _event.emit(PostsEvent.CreateComment(text = "успешно"))
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                    _event.emit(PostsEvent.CreateComment(text = "ошибка при отправке коммента"))
                }
            )
            resetPostId()
        }

    }

    private fun savePost(postId: String) {
        viewModelScope.launch {
            val result = savePostByPostIdUseCase.invoke(postId = postId)
            result.fold(
                onSuccess = {
                    _event.emit(PostsEvent.SavePost(text = "успешно"))
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                    _event.emit(PostsEvent.SavePost(text = "ошибка при сохранении поста"))
                }
            )
        }
    }

    private fun likePost(postId: String) {
        viewModelScope.launch {
            val result = likePostUseCase.invoke(postId = postId)
            result.fold(
                onSuccess = { model ->
                    _state.update { currentState ->
                        currentState.copy(
                            listOfPosts = currentState.listOfPosts.map { post ->
                                if (post.postId == postId) {
                                    post.copy(likesCount = model.likesCount)
                                } else {
                                    post
                                }
                            }
                        )
                    }
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                }
            )
        }
    }

    private fun updatePostId(postId: String) {
        _state.update {
            it.copy(postId = postId)
        }
    }

    private fun updateBottomSheetState() {
        _state.update {
            it.copy(showBottomSheet = !state.value.showBottomSheet)
        }
    }

    private fun updateCommentMessage(input: String) {
        _state.update {
            it.copy(commentText = input)
        }
    }

    private fun resetPostId() {
        _state.update {
            it.copy(postId = "")
        }
    }
}

