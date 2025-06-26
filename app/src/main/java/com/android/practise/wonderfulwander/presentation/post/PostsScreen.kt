package com.android.practise.wonderfulwander.presentation.post

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.ListScreen
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.me.ListItemPost
import com.example.base.action.post.PostsAction
import com.example.base.action.profile.ProfileAction
import com.example.base.event.post.PostsEvent
import com.example.base.model.post.Post
import com.example.base.state.PostsState
import com.example.presentation.viewmodel.PostsViewModel
import dagger.Provides
import kotlinx.coroutines.flow.collect
import org.checkerframework.checker.units.qual.Current
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
                    Toast.makeText(context,event.text,Toast.LENGTH_SHORT).show()
                }

                is PostsEvent.CreateComment -> {
                    Toast.makeText(context,event.text,Toast.LENGTH_SHORT).show()
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