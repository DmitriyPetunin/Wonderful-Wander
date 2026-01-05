package com.example.feature.profile.impl.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feature.profile.api.action.ProfileAction
import com.example.base.model.post.Post
import com.example.feature.profile.api.state.ProfileState
import com.example.ui.components.ListItemPost
import com.example.ui.components.ListScreen

@Composable
fun TabScreen(
    state: ProfileState,
    modifier:Modifier,
    selectedTabIndex:Int,
    onTabSelected: (Int) -> Unit,
    onAction: (ProfileAction) -> Unit
) {
    val tabs = listOf("сохранёнки", "мои")

    Column(
        modifier = modifier
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(text = title) }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> SavedPosts(state = state, onAction = onAction)
            1 -> MyPosts(state = state, onAction = onAction)
        }
    }
}

@Composable
fun SavedPosts(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    ListScreen(
        items = state.listOfSavedPosts,
        isLoading = state.isLoading,
        endReached = state.endReachedSavedPosts,
        loadMore = {
            onAction(ProfileAction.LoadMoreSavedPosts)
        },
        itemContent = { post: Post ->
            ListItemPost(
                post = post,
                onPostClick = { onAction(ProfileAction.SubmitPostItem(post.postId)) },
                onSaveClick = { onAction(ProfileAction.SubmitSavePost(post.postId)) },
                onLikeClick = { onAction(ProfileAction.SubmitLikeSavedPost(post.postId)) },
                onFastCommentClick = {},
                onDeleteClick = { onAction(ProfileAction.SubmitDeleteSavedPost(post.postId))},
                showDeleteButton = state.isItMyProfile,
                showSaveButton = !state.isItMyProfile
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
    )
}

@Composable
fun MyPosts(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {
    ListScreen(
        items = state.listOfMyPosts,
        isLoading = state.isLoading,
        endReached = state.endReachedMyPosts,
        loadMore = {
            onAction(ProfileAction.LoadMoreMyPosts)
        },
        itemContent = { post: Post ->
            ListItemPost(
                post = post,
                onPostClick = { onAction(ProfileAction.SubmitPostItem(post.postId)) },
                onSaveClick = { onAction(ProfileAction.SubmitSavePost(post.postId)) },
                onLikeClick = { onAction(ProfileAction.SubmitDeleteMyPost(post.postId)) },
                onFastCommentClick = {},
                onDeleteClick = { onAction(ProfileAction.SubmitDeleteMyPost(post.postId)) },
                showDeleteButton = state.isItMyProfile,
                showSaveButton = !state.isItMyProfile
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
    )
}