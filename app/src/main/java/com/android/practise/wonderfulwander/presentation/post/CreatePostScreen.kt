package com.android.practise.wonderfulwander.presentation.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.base.action.post.CreatePostAction
import com.example.base.event.post.CreatePostEvent
import com.example.base.state.CreatePostState
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun CreatePostScreenRoute(
    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {

    val state by createPostViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        createPostViewModel.event.collect{ event->
            when(event){
                CreatePostEvent.NavigateToUploadImageScreen -> {

                }
            }
        }
    }

    CreatePostScreen(state,createPostViewModel::onAction)
}




@Composable
fun CreatePostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Создать новый пост",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        //Поле ввода описания
//        OutlinedTextField(
//            value = state.description,
//            onValueChange = { onAction(CreatePostAction.UpdateDescription(it)) },
//            label = { Text("Добавьте описание") },
//            modifier = Modifier.fillMaxWidth(),
//            maxLines = 5,
//            shape = MaterialTheme.shapes.medium
//        )
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            UploadPhotoPostScreen(state = state,onAction = onAction, modifier = Modifier.size(500.dp))
        }
    }

}