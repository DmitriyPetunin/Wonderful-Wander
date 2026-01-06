package com.example.feature.profile.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.base.R
import com.example.domain.model.post.Post
import com.example.feature.profile.api.action.ProfileAction
import com.example.feature.profile.api.state.ProfileState
import com.example.ui.components.ListScreen
import kotlin.toString

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

@Composable
fun ListItemPost(
    post: Post,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    onPostClick: () -> Unit,
    onLikeClick: () -> Unit,
    onFastCommentClick: () -> Unit,
    showDeleteButton: Boolean,
    showSaveButton:Boolean,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onPostClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
        ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {

        Column {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            AsyncImage(
                model = post.photoUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = { onLikeClick() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Лайк",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = post.likesCount.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = { onFastCommentClick() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_comment),
                            contentDescription = "Комментарии",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Text(
                        text = post.commentsCount.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                if (showDeleteButton) {
                    IconButton(
                        onClick = { onDeleteClick() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                if (showSaveButton) {
                    IconButton(
                        onClick = { onSaveClick() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Сохранить",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
                Text(
                    text = post.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}