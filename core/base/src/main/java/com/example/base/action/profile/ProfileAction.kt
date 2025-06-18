package com.example.base.action.profile

import android.net.Uri


sealed class ProfileAction {
    data object Init:ProfileAction()
    data object SubmitGetAllFriends: ProfileAction()
    data object SubmitGetAllFollowing: ProfileAction()
    data object SubmitGetAllFollowers: ProfileAction()
    data object SignOut: ProfileAction()
    data object SubmitUpdateProfileInfo: ProfileAction()
    data object SubmitDeleteProfile: ProfileAction()
    data object LoadMore:ProfileAction()


    data class SubmitPostItem(val postId:String):ProfileAction()
    data class SubmitUploadAvatar(val input:Uri):ProfileAction()
    data class UpdateSelectedTab(val index:Int) :ProfileAction()
    data class UpdateDropDawnVisible(val isVisible:Boolean): ProfileAction()
    data class SubmitBellIcon(val input: String):ProfileAction()

}