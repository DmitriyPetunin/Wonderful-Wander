package com.example.base.action.profile

import android.net.Uri


sealed class ProfileAction {

    data object SubmitGetAllFriends: ProfileAction()
    data object SubmitGetAllFollowing: ProfileAction()
    data object SubmitGetAllFollowers: ProfileAction()
    data object SignOut: ProfileAction()
    data object SubmitUpdateProfileInfo: ProfileAction()
    data object SubmitDeleteProfile: ProfileAction()
    data class SubmitUploadAvatar(val input:Uri):ProfileAction()

    data class UpdateDropDawnVisible(val isVisible:Boolean): ProfileAction()

    data class SubmitBellIcon(val input: String):ProfileAction()

    data object Init:ProfileAction()
}