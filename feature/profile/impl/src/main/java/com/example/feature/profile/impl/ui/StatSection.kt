package com.example.feature.profile.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.feature.profile.api.action.ProfileAction
import com.example.feature.profile.api.state.ProfileState

@Composable
fun StatSection(
    state: ProfileState,
    modifier: Modifier = Modifier,
    onAction: (ProfileAction) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {

        ProfileStat(
            numberText = state.followersCount.toString(),
            text = "followers",
            onClick = {
                if (state.isItMyProfile){
                    onAction(ProfileAction.SubmitGetAllFollowers)
                }
            }
        )
        ProfileStat(
            numberText = state.friendsCount.toString(),
            text = "friends",
            onClick = {
                if (state.isItMyProfile){
                    onAction(ProfileAction.SubmitGetAllFriends)
                }
            }
        )
        ProfileStat(
            numberText = state.followingCount.toString(),
            text = "followed",
            onClick = {
                if (state.isItMyProfile){
                    onAction(ProfileAction.SubmitGetAllFollowing)
                }
            }
        )

    }
}