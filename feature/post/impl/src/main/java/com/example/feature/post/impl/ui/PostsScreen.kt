package com.example.feature.post.impl.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.base.R
import com.example.ui.components.ListScreen
import com.example.base.util.showToast
import com.example.domain.model.post.Post
import com.example.feature.post.api.action.PostsAction
import com.example.feature.post.api.event.PostsEvent
import com.example.feature.post.api.state.PostsState
import com.example.feature.post.impl.viewmodel.PostsViewModel
import com.example.base.R as baseR


@Composable
fun PostsScreenRoute(
    navigateToCreatePost: () -> Unit,
    navigateToDetailPost: (String) -> Unit,
    postsViewModel: PostsViewModel = hiltViewModel(),
){

    val state by postsViewModel.state.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        postsViewModel.onAction(PostsAction.LoadMorePosts)
    }

    LaunchedEffect(Unit) {
        postsViewModel.event.collect{ event ->
            when(event){
                PostsEvent.NavigateToCreatePost -> {
                    navigateToCreatePost()
                }

                is PostsEvent.NavigateToDetailPost -> {
                    navigateToDetailPost(event.postId)
                }

                is PostsEvent.SavePost -> {
                    context.showToast(event.text)
                }

                is PostsEvent.CreateComment -> {
                    context.showToast(event.text)
                }
            }
        }
    }
    PostsScreen(state,postsViewModel::onAction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    state:PostsState,
    onAction: (PostsAction) -> Unit
) {

    val sheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(baseR.string.photosScreen),
                style = MaterialTheme.typography.displayMedium
            )
            ListOfPosts(state = state,onAction = onAction)
        }
        Button(
            modifier = Modifier
                .padding(bottom = 24.dp, end = 12.dp)
                .align(Alignment.BottomEnd)
            ,
            onClick = {
                onAction(PostsAction.SubmitCreatePost)
            }
        ) {
            Text("Создать пост")
        }
        if(state.showBottomSheet){
            TextFieldBottomSheet(
                state = sheetState,
                currentText = state.commentText,
                onDismiss = { onAction(PostsAction.UpdateBottomSheetVisible) },
                onSuccess = { onAction(PostsAction.SuccessWritingCommentForPost)},
                onTextChanged = { text -> onAction(PostsAction.UpdateCommentMessage(text)) },
            )
        }
    }
}

@Composable
private fun ListOfPosts(
    state: PostsState,
    onAction:(PostsAction) -> Unit
){
    ListScreen(
        items = state.listOfPosts,
        isLoading = state.isLoading,
        endReached = state.endReachedPosts,
        loadMore = {
            onAction(PostsAction.LoadMorePosts)
        },
        itemContent = { post: Post ->
            ListItemPost(
                post = post,
                onPostClick = { onAction(PostsAction.SubmitPostItem(post.postId)) },
                onSaveClick = { onAction(PostsAction.SubmitSavePost(post.postId)) },
                onLikeClick = { onAction(PostsAction.SubmitLikePost(post.postId)) },
                onFastCommentClick = { onAction(PostsAction.FastCommentClick(postId = post.postId)) },
                onDeleteClick = { },
                showDeleteButton = false,
                showSaveButton = true
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldBottomSheet(
    state: SheetState,
    currentText: String,
    onDismiss: () -> Unit,
    onSuccess:() -> Unit,
    onTextChanged: (String) -> Unit,
    hint: String = "Введите свой комментарий"
){
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = state
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            OutlinedTextField(
                value = currentText,
                onValueChange = onTextChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text(hint) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onDismiss() }
                ),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Button(
                onClick = onSuccess,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Готово")
            }
        }
    }
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
