package com.example.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.post.CreatePostAction
import com.example.base.event.post.CreatePostEvent
import com.example.base.model.post.category.Category
import com.example.base.state.CreatePostState
import com.example.base.state.PhotoState
import com.example.presentation.usecase.GetAllCategoriesUseCase
import com.example.presentation.usecase.UploadPostPhotoUseCase
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
class CreatePostViewModel @Inject constructor(
    private val uploadPostPhotoUseCase: UploadPostPhotoUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
):ViewModel() {

    private val _state = MutableStateFlow(CreatePostState())
    val state:StateFlow<CreatePostState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<CreatePostEvent>()
    val event:SharedFlow<CreatePostEvent> = _event.asSharedFlow()


    fun onAction(action: CreatePostAction){
        when(action){
            CreatePostAction.Init -> {
                getAllCategories()
            }
            is CreatePostAction.UpdatePhotoUri -> {
                updatePhotoUri(action.input)
                sendPhotoToServer()
            }

            is CreatePostAction.UpdateQueryParam -> {
                updateQueryParam(action.input)
            }
            is CreatePostAction.UpdateTitle -> {
                updateTitle(action.input)
            }
            is CreatePostAction.UpdateSelectedCategory -> {
                updateSelectedCategory(action.category)
            }

            is CreatePostAction.UpdateActiveParam -> {
                updateActiveSearchBar(action.active)
            }
        }
    }

    private fun sendPhotoToServer(){
        _state.update {
            it.copy(photoState = PhotoState.Loading)
        }
        viewModelScope.launch {
            delay(1000L)
            val response = uploadPostPhotoUseCase.invoke(state.value.photoUri)

            response.fold(
                onSuccess = { model ->
                    _state.update {
                        it.copy(status = "success")
                    }
                },
                onFailure = { exception ->
                    //обработка
                }
            )
            _state.update {
                it.copy(photoState = PhotoState.Success)
            }
        }
    }
    private fun getAllCategories(){
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = getAllCategoriesUseCase.invoke()

            result.fold(
                onSuccess = {value: List<Category> ->
                    _state.update {
                        it.copy(listOfCategories = value, isLoading = false)
                    }
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
            )
        }
    }

    private fun updatePhotoUri(uri: Uri){
        _state.update {
            it.copy(photoUri = uri)
        }
    }
    private fun updateQueryParam(input: String){
        _state.update {
            it.copy(queryParam = input)
        }
    }

    private fun updateTitle(input: String){
        _state.update {
            it.copy(title = input)
        }
    }

    private fun updateSelectedCategory(category: Category?){
        _state.update {
            it.copy(selectedCategory = category)
        }
    }
    private fun updateActiveSearchBar(active:Boolean){
        _state.update {
            it.copy(searchBarIsActive = active)
        }
    }
}