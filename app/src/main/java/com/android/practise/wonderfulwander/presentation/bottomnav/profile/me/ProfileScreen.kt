package com.android.practise.wonderfulwander.presentation.bottomnav.profile.me

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.ListScreen
import com.example.base.R
import com.example.base.action.profile.ProfileAction
import com.example.base.event.profile.ProfileEvent
import com.example.base.model.post.PostResult
import com.example.base.state.PeopleEnum
import com.example.base.state.ProfileState
import com.example.presentation.viewmodel.ProfileViewModel
import com.example.base.R as baseR

@Composable
fun ProfileScreenRoute(
    navigateToAuthScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToPeopleScreen: (PeopleEnum) -> Unit,
    navigateToUpdateScreen: () -> Unit,
    navigateToPostDetailInfoScreen: (String) -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {

    val state by profileViewModel.stateProfile.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.onAction(ProfileAction.Init)
    }

    LaunchedEffect(Unit) {
        profileViewModel.event.collect { event ->
            when (event) {
                is ProfileEvent.NavigateToFriendsPage -> {
                    navigateToPeopleScreen(PeopleEnum.FRIENDS)
                    Toast.makeText(context, "friends", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToAuthPage -> {
                    navigateToAuthScreen()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToFollowersPage -> {
                    navigateToPeopleScreen(PeopleEnum.FOLLOWERS)
                    Toast.makeText(context, "followers", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToFollowingPage -> {
                    navigateToPeopleScreen(PeopleEnum.FOLLOWING)
                    Toast.makeText(context, "following", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToUpdateScreenPage -> {
                    navigateToUpdateScreen()
                    Toast.makeText(context, "NavigateToUpdateScreenPage", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.ShowError -> {
                    navigateToAuthScreen()
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.NavigateToRegisterPage -> {
                    navigateToRegisterScreen()
                    Toast.makeText(context, "NavigateToRegisterPage", Toast.LENGTH_SHORT).show()
                }
                is ProfileEvent.NavigateToPostDetail -> {
                    navigateToPostDetailInfoScreen(event.postId)
                    Toast.makeText(context, "NavigateToPostDetail", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.DeletePost -> {
                    Toast.makeText(context, "пост с id ${event.postId} был удалён", Toast.LENGTH_SHORT).show()
                }

                is ProfileEvent.SavePost -> {
                    Toast.makeText(context, "пост с id ${event.postId} был сохранён", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    ProfileScreen(state = state, profileViewModel::onAction)


}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        MeTopBar(
            username = state.username,
            modifier = Modifier.fillMaxWidth(),
            updateDropDawnVisible = { onAction(ProfileAction.UpdateDropDawnVisible(isVisible = !state.dropDownMenuVisible))},
            visibleState = state.dropDownMenuVisible,
            onAction = onAction
        )

        Text(
            text = "Profile",
            modifier = Modifier.align(Alignment.Start),
            style = MaterialTheme.typography.displayLarge
        )
        CustomAvatar(
            avatarUrl = state.avatarUrl,
            onAction = onAction
        )
        if (state.username.isNotEmpty()) {
            Text(
                text = state.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        StatSection(state = state,onAction = onAction, modifier = Modifier.weight(0.5f))


        TabScreen(
            state = state,
            modifier = Modifier.weight(1.5f),
            selectedTabIndex = state.selectedTabIndex,
            onTabSelected = { index -> onAction(ProfileAction.UpdateSelectedTab(index)) },
            onAction = onAction
        )
    }
}


@Composable
fun MeTopBar(
    username: String,
    modifier: Modifier = Modifier,
    updateDropDawnVisible: () -> Unit,
    visibleState:Boolean,
    onAction: (ProfileAction) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = username,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .padding(horizontal = 48.dp)
        )
        Icon(
            painter = painterResource(id = baseR.drawable.ic_dotmenu),
            contentDescription = "menu",
            tint = Color.Black,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .clickable { updateDropDawnVisible() }
        )
        // Выпадающее меню
        Box(
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            CustomDropDawnMenu(
                expanded = visibleState,
                onDismissRequest = { updateDropDawnVisible() },
                onAction = onAction
            )
        }
    }
}



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

@Composable
fun ProfileStat(
    numberText: String,
    text:String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){

    Column(
        modifier = modifier.clickable {
            onClick()
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = numberText,
            fontWeight = FontWeight.Bold,
            fontSize =  20.sp
            )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = text)

    }

}
@Composable
fun CustomAvatar(
    avatarUrl: String,
    onAction: (ProfileAction) -> Unit
){

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri == null) {
            //закрыли галлерию
            Log.d("PickImage", "No image selected")
        } else {
            onAction(ProfileAction.SubmitUploadAvatar(uri))
        }
    }

    if (avatarUrl.isNotEmpty()) {
        AsyncImage(
            model = avatarUrl,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
            error = painterResource(R.drawable.ic_visibility_off_foreground),
        )
    } else {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Default profile icon",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .clickable { pickImageLauncher.launch("image/*") }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

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
            0 -> Tab1Content(state = state, onAction = onAction)
            1 -> Tab2Content(state = state, onAction = onAction)
        }
    }
}

@Composable
fun Tab1Content(
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
        itemContent = { postResult: PostResult ->
            ListItemPost(
                postResult = postResult,
                onPostClick = { onAction(ProfileAction.SubmitPostItem(postResult.postId)) },
                onSaveClick = { onAction(ProfileAction.SubmitSavePost(postResult.postId)) },
                onLikeClick = {},
                onCommentClick = {},
                onDeleteClick = { onAction(ProfileAction.SubmitDeleteSavedPost(postResult.postId))},
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
fun Tab2Content(
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
        itemContent = { postResult: PostResult ->
            ListItemPost(
                postResult = postResult,
                onPostClick = { onAction(ProfileAction.SubmitPostItem(postResult.postId)) },
                onSaveClick = { onAction(ProfileAction.SubmitSavePost(postResult.postId)) },
                onLikeClick = {},
                onCommentClick = {},
                onDeleteClick = { onAction(ProfileAction.SubmitDeleteMyPost(postResult.postId)) },
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
fun CustomDropDawnMenu(
    expanded:Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onAction:(ProfileAction) -> Unit,
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .width(IntrinsicSize.Min),
        offset = DpOffset(x = 0.dp, y = 8.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        DropdownMenuItem(
            text = { Text("выйти") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                onAction(ProfileAction.SignOut)
            },
        )
        DropdownMenuItem(
            text = { Text("изменить") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                onAction(ProfileAction.SubmitUpdateProfileInfo)
            }
        )
        DropdownMenuItem(
            text = { Text("удалить") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            onClick = {
                onAction(ProfileAction.SubmitDeleteProfile)
            }
        )
    }
}

@Composable
fun ListItemPost(
    postResult: PostResult,
    onDeleteClick: () -> Unit,
    onSaveClick: () -> Unit,
    onPostClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
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
                text = postResult.postId,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            AsyncImage(
                model = postResult.photoUrl,
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
                        text = postResult.likesCount.toString(),
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
                        onClick = { onCommentClick() },
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
                        text = postResult.commentsCount.toString(),
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
                    text = postResult.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}