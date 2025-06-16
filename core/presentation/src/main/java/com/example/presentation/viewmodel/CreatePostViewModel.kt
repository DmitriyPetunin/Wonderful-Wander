package com.example.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.post.CreatePostAction
import com.example.base.event.post.CreatePostEvent
import com.example.base.state.CreatePostState
import com.example.base.state.PhotoState
import com.example.presentation.usecase.UploadPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase
):ViewModel() {

    private val _state = MutableStateFlow(CreatePostState())
    val state:StateFlow<CreatePostState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<CreatePostEvent>()
    val event:SharedFlow<CreatePostEvent> = _event


    fun onAction(action: CreatePostAction){
        when(action){
            is CreatePostAction.UpdatePhotoUri -> {
                updatePhotoUri(action.input)
                sendPhotoToServer()
            }

            CreatePostAction.SubmitToAddPhoto -> {
                viewModelScope.launch {
                    _event.emit(CreatePostEvent.NavigateToUploadImageScreen)
                }
            }
        }
    }

    private fun sendPhotoToServer(){
        _state.update {
            it.copy(photoState = PhotoState.Loading)
        }
        viewModelScope.launch {
            delay(3000L)
            val response = uploadPhotoUseCase.invoke(state.value.photoUri)

            response.fold(
                onSuccess = { model ->
                    _state.update {
                        it.copy(fileName = model.fileName)
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


    private fun updatePhotoUri(uri: Uri){
        _state.update {
            it.copy(photoUri = uri)
        }
    }

}