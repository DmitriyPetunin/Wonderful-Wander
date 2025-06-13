package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.UpdateProfileAction
import com.example.base.action.profile.ProfileAction
import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility
import com.example.base.event.profile.ProfileEvent
import com.example.base.model.user.profile.UpdateProfileParam
import com.example.base.state.ProfileState
import com.example.base.state.UpdateProfileState
import com.example.presentation.googleclient.GoogleAuthUiClient
import com.example.presentation.usecase.DeleteUserProfileUseCase
import com.example.presentation.usecase.FollowToUserByIdUseCase
import com.example.presentation.usecase.GetPersonProfileInfoByIdUseCase
import com.example.presentation.usecase.UnFollowToUserByIdUseCase
import com.example.presentation.usecase.UpdateProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val unFollowToUserByIdUseCase: UnFollowToUserByIdUseCase,
    private val followToUserByIdUseCase: FollowToUserByIdUseCase,
    private val getPersonProfileInfoByIdUseCase: GetPersonProfileInfoByIdUseCase,
    private val deleteUserProfileUseCase: DeleteUserProfileUseCase,
    private val updateUserUseCase: UpdateProfileInfoUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient,
) : ViewModel() {

    private val _stateProfile: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val stateProfile = _stateProfile.asStateFlow()

    private val _stateUpdateProfile: MutableStateFlow<UpdateProfileState> = MutableStateFlow(UpdateProfileState())
    val stateUpdateProfile = _stateUpdateProfile.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event



    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.SignOut -> {
                signOut()
                resetState()
            }

            ProfileAction.SubmitGetAllFriends -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToFriendsPage)
                }
            }

            ProfileAction.SubmitGetAllFollowers -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToFollowersPage)
                }
            }

            ProfileAction.SubmitGetAllFollowing -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToFollowingPage)
                }
            }

            ProfileAction.SubmitUpdateProfileInfo -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToUpdateScreenPage)
                }
            }

            ProfileAction.SubmitDeleteProfile -> {
                deleteProfile()
            }

            is ProfileAction.UpdateDropDawnVisible -> {
                _stateProfile.update {
                    it.copy(dropDownMenuVisible = action.isVisible)
                }
            }

            is ProfileAction.SubmitBellIcon -> {
                submitToBellIcon(action.input)
            }
        }
    }

    fun onActionUpdate(action: UpdateProfileAction) {
        when (action) {
            is UpdateProfileAction.UpdateBioField -> {
                updateBioState(action.input)
            }

            is UpdateProfileAction.UpdateEmailField -> {
                updateEmailState(action.input)
            }

            is UpdateProfileAction.UpdateFirstNameField -> {
                updateFirstNameState(action.input)
            }

            is UpdateProfileAction.UpdateLastNameField -> {
                updateLastNameState(action.input)
            }

            is UpdateProfileAction.UpdatePhotoVisibilityField -> {
                updatePhotoVisibilityState(action.input)
            }

            is UpdateProfileAction.UpdateWalkVisibilityField -> {
                updateWalkVisibilityState(action.input)
            }

            UpdateProfileAction.SubmitSaveButton -> {
                updateProfileInfo()
            }
        }
    }

    fun initUpdateState() {
        _stateUpdateProfile.update { update ->
            with(_stateProfile.value) {
                update.copy(
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    bio = bio,
                    photoVisibility = photoVisibility,
                    walkVisibility = walkVisibility
                )
            }
        }
    }

    fun getSignedInUser() {
        val userData = googleAuthUiClient.getSignedInUser()
        userData?.let {
            _stateProfile.update {
                it.copy(
                    userId = userData.userId,
                    username = userData.username,
                    avatarUrl = userData.profilePictureUrl
                )
            }
        }
    }

    fun getPersonProfileInfoById(info: String) {
        val (id,whoIsIt) = info.split(" ")

        viewModelScope.launch {
            val response = getPersonProfileInfoByIdUseCase.invoke(id)

            response.fold(
                onSuccess = { model ->
                    _stateProfile.update {
                        it.copy(username = model.userName, avatarUrl = model.avatarUrl, isFollowing = followingState(whoIsIt))
                    }
                },
                onFailure = { exception ->
                    Log.d("TEST-TAG","${exception.message}")
                    exception.printStackTrace()
                }
            )
        }
    }

    private fun deleteProfile() {
        viewModelScope.launch {

            val response = deleteUserProfileUseCase.invoke()

            response.fold(
                onSuccess = {
                    _event.emit(ProfileEvent.NavigateToRegisterPage)
                    resetState()
                },
                onFailure = { exception ->
                    exception.message?.let {
                        Log.d("TEST-TAG", it)
                        _event.emit(ProfileEvent.ShowError(message = it))
                    }
                }
            )
        }
    }

    private fun submitToBellIcon(id: String){
        val isFollowing = stateProfile.value.isFollowing

        viewModelScope.launch {
            val result = if (isFollowing) unFollowToUserByIdUseCase.invoke(id = id) else followToUserByIdUseCase.invoke(id=id)
            result.fold(
                onSuccess = {
                    _stateProfile.update { currentState ->
                        currentState.copy(isFollowing = !currentState.isFollowing)
                    }
                },
                onFailure = {}
            )
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            _event.emit(ProfileEvent.NavigateToAuthPage)
        }
    }

    private fun updateProfileInfo() {
        viewModelScope.launch {

            val response = updateUserUseCase.invoke(
                UpdateProfileParam(
                    email = stateUpdateProfile.value.email,
                    firstName = stateUpdateProfile.value.firstName,
                    lastName = stateUpdateProfile.value.lastName,
                    bio = stateUpdateProfile.value.bio,
                    photoVisibility = PhotosVisibility.fromString(stateUpdateProfile.value.photoVisibility),
                    walkVisibility = WalkVisibility.fromString(stateUpdateProfile.value.walkVisibility),
                )
            )
            _stateProfile.update { currentState ->
                response.fold(
                    onSuccess = { result ->
                        currentState.copy(
                            userId = result.userId,
                            email = result.email,
                            firstName = result.firstname,
                            lastName = result.lastname,
                            username = result.username,
                            bio = result.bio,
                            followersCount = result.followersCount,
                            followingCount = result.followingCount,
                            friendsCount = result.friendsCount,
                            avatarUrl = result.avatarUrl,
                            photoVisibility = PhotosVisibility.toStringValue(result.photoVisibility),
                            walkVisibility = WalkVisibility.toStringValue(result.walkVisibility)
                        )
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState
                    }
                )
            }
            _event.emit(ProfileEvent.UpdateProfileInfo)
        }
    }


    private fun resetState() {
        _stateProfile.update { ProfileState() }
    }

    private fun updateEmailState(input: String) {
        _stateUpdateProfile.update {
            it.copy(email = input)
        }
    }

    private fun updateFirstNameState(input: String) {
        _stateUpdateProfile.update {
            it.copy(firstName = input)
        }
    }

    private fun updateLastNameState(input: String) {
        _stateUpdateProfile.update {
            it.copy(lastName = input)
        }
    }

    private fun updateBioState(input: String) {
        _stateUpdateProfile.update {
            it.copy(bio = input)
        }
    }

    private fun updatePhotoVisibilityState(input: String) {
        _stateUpdateProfile.update {
            it.copy(photoVisibility = input)
        }
    }

    private fun updateWalkVisibilityState(input: String) {
        _stateUpdateProfile.update {
            it.copy(walkVisibility = input)
        }
    }

    private fun followingState(info:String):Boolean{
        return when(info){
            "Followers" -> {
                false
            }

            else ->{
                true
            }
        }
    }
}