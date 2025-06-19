package com.example.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.UpdateProfileAction
import com.example.base.action.profile.ProfileAction
import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility
import com.example.base.event.profile.ProfileEvent
import com.example.base.model.post.PostResult
import com.example.base.model.user.People
import com.example.base.model.user.profile.UpdateProfileParam
import com.example.base.state.ProfileState
import com.example.base.state.UpdateProfileState
import com.example.presentation.googleclient.GoogleAuthUiClient
import com.example.presentation.usecase.DeletePostFromMyPostsUseCase
import com.example.presentation.usecase.DeletePostFromMySavedPostsUseCase
import com.example.presentation.usecase.DeleteUserProfileUseCase
import com.example.presentation.usecase.FollowToUserByIdUseCase
import com.example.presentation.usecase.GetMyPostsUseCase
import com.example.presentation.usecase.GetPersonProfileInfoByIdUseCase
import com.example.presentation.usecase.GetPostsByUserIdUseCase
import com.example.presentation.usecase.GetProfileInfoUseCase
import com.example.presentation.usecase.GetSavedPostsByUserIdUseCase
import com.example.presentation.usecase.GetSavedPostsUseCase
import com.example.presentation.usecase.UnFollowToUserByIdUseCase
import com.example.presentation.usecase.UpdateProfileInfoUseCase
import com.example.presentation.usecase.UploadAvatarPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val updateUserProfileUseCase: UpdateProfileInfoUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val uploadAvatarPhotoUseCase: UploadAvatarPhotoUseCase,
    private val getSavedPostsUseCase: GetSavedPostsUseCase,
    private val getMyPostsUseCase: GetMyPostsUseCase,
    private val deletePostFromMySavedPostsUseCase: DeletePostFromMySavedPostsUseCase,
    private val deletePostFromMyPostsUseCase: DeletePostFromMyPostsUseCase,
    private val getSavedPostsByUserIdUseCase: GetSavedPostsByUserIdUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase
) : ViewModel() {

    private val _stateProfile: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val stateProfile = _stateProfile.asStateFlow()

    private val _stateUpdateProfile: MutableStateFlow<UpdateProfileState> = MutableStateFlow(UpdateProfileState())
    val stateUpdateProfile = _stateUpdateProfile.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event



    fun onAction(action: ProfileAction) {
        when (action) {

            ProfileAction.Init -> {
                Log.d("Init", "loadDataForTab")
                getProfileInfo()
                loadDataForTab()
            }

            ProfileAction.SignOut -> {
                signOut()
                resetState()
            }

            ProfileAction.SubmitGetAllFriends -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToFriendsPage)
                }
                resetState()
            }

            ProfileAction.SubmitGetAllFollowers -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToFollowersPage)
                }
                resetState()
            }

            ProfileAction.SubmitGetAllFollowing -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToFollowingPage)
                }
                resetState()
            }

            ProfileAction.SubmitUpdateProfileInfo -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToUpdateScreenPage)
                }
                resetState()
            }

            ProfileAction.SubmitDeleteProfile -> {
                deleteProfile()
            }
            ProfileAction.LoadMoreSavedPosts -> {
                updateEndReachedSavedPostsState()
                loadSavedPosts()
            }
            ProfileAction.LoadMoreMyPosts -> {
                Log.d("LoadMoreMyPosts", "loadMyPosts")
                updateEndReachedMyPostsState()
                loadMyPosts()
            }

            is ProfileAction.UpdateDropDawnVisible -> {
                _stateProfile.update {
                    it.copy(dropDownMenuVisible = action.isVisible)
                }
            }

            is ProfileAction.SubmitBellIcon -> {
                submitToBellIcon(action.input)
            }

            is ProfileAction.SubmitUploadAvatar -> {
                updatePhotoUri(action.input)
                sendPhotoToServer()
                getProfileInfo()
            }

            is ProfileAction.UpdateSelectedTab -> {
                Log.d("UpdateSelectedTab", "loadDataForTab")
                updateSelectedTabState(action.index)
                loadDataForTab()
            }

            is ProfileAction.SubmitPostItem -> {
                viewModelScope.launch {
                    _event.emit(ProfileEvent.NavigateToPostDetail(action.postId))
                }
            }
            is ProfileAction.SubmitDeleteSavedPost -> {
                deleteMySavedPost(action.postId)
            }
            is ProfileAction.SubmitDeleteMyPost -> {
                deleteMyPost(action.postId)
            }

            is ProfileAction.UpdateUserId -> {
                Log.d("UpdateUserId", "loadDataForTab")
                getPersonProfileInfoById(action.userId)
                loadDataForTab()
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
            is UpdateProfileAction.UpdateSavedPhotoVisibilityField -> {
                updateSavedPhotoVisibilityState(action.input)
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
                    myPhotoVisibility = myPhotoVisibility,
                    savedPhotoVisibility = savedPhotoVisibility,
                    walkVisibility = walkVisibility
                )
            }
        }
    }

    fun getPersonProfileInfoById(id: String) {
        Log.d("USERID", "getPersonProfileInfoById: $id")
        _stateProfile.update {
            it.copy(userId = id, isItMyProfile = false, isLoading = true)
        }

        viewModelScope.launch {
            val response = getPersonProfileInfoByIdUseCase.invoke(id)

            response.fold(
                onSuccess = { model ->
                    _stateProfile.update {
                        it.copy(
                            userId = id,
                            username = model.userName,
                            firstName = model.firstname,
                            lastName = model.lastname,
                            bio = model.bio,
                            avatarUrl = model.avatarUrl,
                            isFriends = model.isFriends,
                            isFollowedByUser = model.isFollowedByUser
                        )
                    }
                },
                onFailure = { exception ->
                    Log.d("TEST-TAG","${exception.message}")
                    exception.printStackTrace()
                }
            )
            _stateProfile.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun updatePhotoUri(uri: Uri){
        _stateProfile.update {
            it.copy(photoUri = uri)
        }
    }
    private fun sendPhotoToServer(){
        _stateProfile.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(1000L)
            val response = uploadAvatarPhotoUseCase.invoke(stateProfile.value.photoUri)

            response.fold(
                onSuccess = { model ->
                    _stateProfile.update {
                        it.copy(status = true)
                    }
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                    _stateProfile.update {
                        it.copy(status = false)
                    }
                }
            )
            _stateProfile.update {
                it.copy(isLoading = false)
            }
        }
    }
    private fun updateSelectedTabState(index:Int){
        _stateProfile.update { currentState ->
            currentState.copy(
                selectedTabIndex = index,
                listOfSavedPosts = emptyList(),
                currentPageSavedPosts = 1,
                isInitialLoadingSavedPosts = true,
                endReachedSavedPosts = false,
                listOfMyPosts = emptyList(),
                currentPageMyPosts = 1,
                isInitialLoadingMyPosts = true,
                endReachedMyPosts = false
            )
        }
    }


    private fun loadDataForTab(){
        val index = stateProfile.value.selectedTabIndex
        when(index) {
            0 -> {
                if(stateProfile.value.isInitialLoadingSavedPosts){
                    loadSavedPosts()
                    updateIsInitialLoadingSavedPostsState()
                }
            }
            1 -> {
                if(stateProfile.value.isInitialLoadingMyPosts){
                    loadMyPosts()
                    updateIsInitialLoadingMyPosts()
                }
            }
        }
    }


    private fun loadSavedPosts(){
        Log.d("LoadSavedPosts", "state: ${stateProfile.value}")
        if ((!stateProfile.value.isInitialLoadingSavedPosts && stateProfile.value.isLoading) || stateProfile.value.endReachedSavedPosts) return

        _stateProfile.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000L)
            val result = if (stateProfile.value.isItMyProfile) {
                getSavedPostsUseCase.invoke(stateProfile.value.currentPageSavedPosts,stateProfile.value.limit)
            } else getSavedPostsByUserIdUseCase.invoke(userId = stateProfile.value.userId, page = stateProfile.value.currentPageSavedPosts, limit = stateProfile.value.limit)

            _stateProfile.update { currentState ->
                result.fold(
                    onSuccess = { newPosts:List<PostResult> ->
                        //Log.d("LoadSavedPosts", "loadSavedPosts:${newPosts.map { it.toString() }} ")
                        if(!stateProfile.value.isInitialLoadingSavedPosts && newPosts.lastOrNull() == stateProfile.value.listOfSavedPosts.lastOrNull()){
                            currentState.copy(
                                isLoading = false,
                                endReachedSavedPosts = true
                            )
                        } else {
                            currentState.copy(
                                listOfSavedPosts = currentState.listOfSavedPosts + newPosts,
                                isLoading = false,
                                currentPageSavedPosts = currentState.currentPageSavedPosts + 1,
                                endReachedSavedPosts = newPosts.size < currentState.limit
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
    private fun loadMyPosts(){
        if ((!stateProfile.value.isInitialLoadingMyPosts && stateProfile.value.isLoading) || stateProfile.value.endReachedMyPosts) return
        _stateProfile.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            delay(1000L)
            val result = if (stateProfile.value.isItMyProfile) {
                getMyPostsUseCase.invoke(stateProfile.value.currentPageMyPosts, stateProfile.value.limit)
            } else getPostsByUserIdUseCase.invoke(stateProfile.value.userId,stateProfile.value.currentPageMyPosts, stateProfile.value.limit)

            _stateProfile.update { currentState ->
                result.fold(
                    onSuccess = { newPosts:List<PostResult> ->
                        if(!stateProfile.value.isInitialLoadingMyPosts && newPosts.lastOrNull() == stateProfile.value.listOfMyPosts.lastOrNull()){
                            currentState.copy(
                                isLoading = false,
                                endReachedMyPosts = true
                            )
                        } else {
                            currentState.copy(
                                listOfMyPosts = currentState.listOfMyPosts + newPosts,
                                isLoading = false,
                                currentPageMyPosts = currentState.currentPageMyPosts + 1,
                                endReachedMyPosts = newPosts.size < currentState.limit
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
    private fun deleteMyPost(postId:String){
        viewModelScope.launch {
            val result = deletePostFromMyPostsUseCase.invoke(postId = postId)
            result.fold(
                onSuccess = {
                    _event.emit(ProfileEvent.DeletePost(postId = postId))
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                }
            )
        }
    }
    private fun deleteMySavedPost(postId:String){
        viewModelScope.launch {
            val result = deletePostFromMySavedPostsUseCase.invoke(postId = postId)
            result.fold(
                onSuccess = {
                    _event.emit(ProfileEvent.DeletePost(postId = postId))
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                }
            )
        }
    }


    private fun getProfileInfo(){
        _stateProfile.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val result = getProfileInfoUseCase.invoke()
            _stateProfile.update { currentState ->
                result.fold(
                    onSuccess = { model ->
                        Log.d("PROFILE", model.avatarUrl)
                        currentState.copy(
                            userId = model.userId,
                            username = model.username,
                            firstName = model.firstname,
                            avatarUrl = model.avatarUrl,
                            lastName = model.lastname,
                            bio = model.bio,
                            followersCount = model.followersCount,
                            friendsCount = model.friendsCount,
                            followingCount = model.followingCount,

                    )},
                    onFailure = {exception ->
                        exception.printStackTrace()
                        currentState
                    }
                )
            }
            _stateProfile.update {
                it.copy(isLoading = false)
            }
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
        val isFollowing = stateProfile.value.isFollowedByUser // я подписан?

        viewModelScope.launch {
            val result = if (isFollowing) unFollowToUserByIdUseCase.invoke(id = id) else followToUserByIdUseCase.invoke(id=id)
            result.fold(
                onSuccess = {
                    _stateProfile.update { currentState ->
                        currentState.copy(isFollowedByUser = !currentState.isFollowedByUser)
                    }
                },
                onFailure = {}
            )
        }
    }

    private fun signOut() {
        viewModelScope.launch {
//            googleAuthUiClient.signOut()
            _event.emit(ProfileEvent.NavigateToAuthPage)
        }
    }

    private fun updateProfileInfo() {
        viewModelScope.launch {

            val response = updateUserProfileUseCase.invoke(
                UpdateProfileParam(
                    email = stateUpdateProfile.value.email,
                    firstName = stateUpdateProfile.value.firstName,
                    lastName = stateUpdateProfile.value.lastName,
                    bio = stateUpdateProfile.value.bio,
                    myPhotoVisibility = PhotosVisibility.fromString(stateUpdateProfile.value.myPhotoVisibility),
                    savedPhotoVisibility = PhotosVisibility.fromString(stateUpdateProfile.value.savedPhotoVisibility),
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
                            myPhotoVisibility = PhotosVisibility.toStringValue(result.myPhotoVisibility),
                            savedPhotoVisibility = PhotosVisibility.toStringValue(result.savedPhotoVisibility),
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

    private fun updateIsInitialLoadingSavedPostsState(){
        _stateProfile.update {
            it.copy(isInitialLoadingSavedPosts = false)
        }
    }

    private fun updateIsInitialLoadingMyPosts(){
        _stateProfile.update {
            it.copy(isInitialLoadingMyPosts = false)
        }
    }


    private fun resetState() {
        _stateProfile.update { ProfileState() }
    }
    private fun updateEndReachedSavedPostsState(){
        _stateProfile.update {
            it.copy(endReachedSavedPosts = false)
        }
    }
    private fun updateEndReachedMyPostsState(){
        _stateProfile.update {
            it.copy(endReachedMyPosts = false)
        }
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
            it.copy(myPhotoVisibility = input)
        }
    }
    private fun updateSavedPhotoVisibilityState(input: String) {
        _stateUpdateProfile.update {
            it.copy(savedPhotoVisibility = input)
        }
    }

    private fun updateWalkVisibilityState(input: String) {
        _stateUpdateProfile.update {
            it.copy(walkVisibility = input)
        }
    }

}