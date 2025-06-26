package com.android.practise.wonderfulwander.presentation.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.ListScreen
import com.example.base.R
import com.example.base.action.post.PostDetailAction
import com.example.base.event.post.PostDetailEvent
import com.example.base.event.profile.ProfileEvent
import com.example.base.model.post.Comment
import com.example.base.state.CommentUi
import com.example.base.state.PostDetailState
import com.example.presentation.viewmodel.PostViewModel


@Composable
fun PostDetailInfoScreenRoute(
    postId: String,
    postViewModel: PostViewModel = hiltViewModel(),
    navigateToPersonProfile: (String) -> Unit
) {

    val state by postViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        postViewModel.onAction(PostDetailAction.UpdatePostId(postId))
    }

    LaunchedEffect(Unit) {
        postViewModel.event.collect { event ->
            when (event) {
                is PostDetailEvent.NavigateToPersonProfile -> {
                    navigateToPersonProfile(event.userId)
                }
            }

        }
    }

    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    } else {
        PostDetailInfoScreen(state = state, postViewModel::onAction)
    }

}

@Composable
fun PostDetailInfoScreen(
    state: PostDetailState,
    onAction: (PostDetailAction) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onAction(PostDetailAction.NavigateBack) }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Назад"
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Детали поста",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
        }


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column {
                AsyncImage(
                    model = state.photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )


                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = state.title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Категория",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = state.categoryName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .clickable { onAction(PostDetailAction.UserClicked(state.user.userId)) }
                    ) {
                        AsyncImage(
                            model = state.user.profilePictureUrl,
                            contentDescription = "Аватар автора",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = state.user.username,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = state.createdAt,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem(
                            painter = painterResource(R.drawable.ic_favorite_filled),
                            count = state.likesCount,
                            onClick = { onAction(PostDetailAction.LikePost) }
                        )
                        StatItem(
                            painter = painterResource(R.drawable.ic_comment),
                            count = state.commentsCount,
                            onClick = { onAction(PostDetailAction.CommentPost) }
                        )
                    }
                }
            }
        }

        if (!state.commentsIsVisible) {
            Button(
                onClick = { onAction(PostDetailAction.ShowAllComments) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text = "Показать все комментарии (${state.commentsCount})")
            }
        } else {
            CommentsList(state = state, onAction = onAction)
        }

    }
}

@Composable
private fun CommentsList(
    state: PostDetailState,
    onAction: (PostDetailAction) -> Unit
) {

    ListScreen(
        items = state.listOfComments,
        isLoading = state.isLoadingComments,
        endReached = state.endReached,
        loadMore = {
            onAction.invoke(PostDetailAction.LoadMoreComments)
        },
        itemContent = { comment: CommentUi ->
            CommentListItem(
                comment = comment,
                onDeleteClick = { onAction.invoke(PostDetailAction.DeleteComment(comment.comment.commentId)) },
                onCommentClick = { onAction.invoke(PostDetailAction.ClickOnComment(comment.comment.commentId)) },
                onUserClick = { onAction.invoke(PostDetailAction.UserClicked(comment.comment.user.userId)) },
            )
        },
    )
}

@Composable
private fun StatItem(
    painter: Painter,
    count: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun CommentListItem(
    comment: CommentUi,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    onUserClick: () -> Unit,
    onCommentClick: () -> Unit,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCommentClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onUserClick() }
            ) {
                AsyncImage(
                    model = comment.comment.user.avatarUrl,
                    contentDescription = "User avatar",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(8.dp))

                Column {
                    Text(
                        text = comment.comment.user.userName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = comment.comment.createdAt,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (comment.canDelete) {
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
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = comment.comment.text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}