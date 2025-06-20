package com.android.practise.wonderfulwander.presentation.post

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.practise.wonderfulwander.presentation.walk.SearchBarCustom
import com.example.base.action.post.CreatePostAction
import com.example.base.event.post.CreatePostEvent
import com.example.base.model.post.category.Category
import com.example.base.state.CreatePostState
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun CreatePostScreenRoute(
    navigateToPhotosScreen: () -> Unit,
    createPostViewModel: CreatePostViewModel = hiltViewModel()
) {

    val state by createPostViewModel.state.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        createPostViewModel.onAction(CreatePostAction.Init)
    }

    LaunchedEffect(Unit) {
        createPostViewModel.event.collect { event ->
            when (event) {
                is CreatePostEvent.ErrorCreatePost -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                CreatePostEvent.SuccessCreatePost -> {
                    navigateToPhotosScreen()
                    Toast.makeText(context, "пост успешно создан", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    } else {
        CreatePostScreen(state, createPostViewModel::onAction)
    }
}


@Composable
fun CreatePostScreen(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Создать новый пост",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CategorySelectionSection(state = state,onAction = onAction)

        PhotoUploadSection(state = state,onAction = onAction)

        DescriptionSection(state = state,onAction = onAction)

        Button(
            onClick = {
                onAction(CreatePostAction.SubmitCreatePost)
            },
            enabled = state.selectedCategory != null && state.fileName.isNotEmpty() && state.title.isNotEmpty()
        ) {
            Text(text = "создать пост")
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    onResultClick: () -> Unit,
) {

    ListItem(
        headlineContent = { Text(text = category.name) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = Modifier
            .clickable { onResultClick() }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    )
}

@Composable
private fun CategorySelectionSection(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Выберите категорию",
            style = MaterialTheme.typography.bodyLarge
        )

        if (state.selectedCategory != null) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = state.selectedCategory!!.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                TextButton(
                    onClick = {
                        onAction(CreatePostAction.UpdateSelectedCategory(null))
                        onAction(CreatePostAction.UpdateActiveParam(true))
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Изменить")
                }
            }
        } else {
            SearchBarCustom(
                query = state.queryParam,
                active = state.searchBarIsActive,
                onActiveChange = { onAction(CreatePostAction.UpdateActiveParam(it)) },
                items = state.listOfCategories,
                onQueryChange = { onAction(CreatePostAction.UpdateQueryParam(it)) },
                searchStringProvider = { it.name },
                itemContent = { category ->
                    CategoryItem(
                        category = category,
                        onResultClick = {
                            onAction(CreatePostAction.UpdateSelectedCategory(category))
                            onAction(CreatePostAction.UpdateActiveParam(false))
                        }
                    )
                }
            )
        }
    }
}

@Composable
private fun PhotoUploadSection(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Добавьте фото",
            style = MaterialTheme.typography.bodyLarge
        )

        UploadPhotoPostScreen(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun DescriptionSection(
    state: CreatePostState,
    onAction: (CreatePostAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Описание",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = state.title,
            onValueChange = { onAction(CreatePostAction.UpdateTitle(it)) },
            label = { Text("Введите описание") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            shape = MaterialTheme.shapes.medium
        )
    }
}