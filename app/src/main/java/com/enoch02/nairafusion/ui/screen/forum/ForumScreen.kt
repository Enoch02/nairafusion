package com.enoch02.nairafusion.ui.screen.forum

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch02.nairafusion.R
import com.enoch02.nairafusion.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A layout that displays the list of topics from the forum
 *
 * @param setTopBarTitle Dynamically modify a [androidx.compose.material3.TopAppBar] outside this composable with a string
 * */
@Composable
fun ForumScreen(
    navController: NavController,
    setTopBarTitle: (Int) -> Unit,
    scope: CoroutineScope,
    modifier: Modifier,
    viewModel: ForumScreenViewModel = viewModel()
) {
    val currentPage by viewModel.currentPage
    val currentForumScreenState by viewModel.currentForumScreenState

    LaunchedEffect(
        key1 = currentPage,
        block = {
            if (currentPage > 0) {
                setTopBarTitle(R.string.app_name)
            } else {
                setTopBarTitle(R.string.featured_topics)
            }

            if (viewModel.topics.isEmpty()) {
                viewModel.refreshPage()
            }
        }
    )

    Crossfade(
        targetState = currentForumScreenState,
        modifier = modifier,
        label = "",
        content = { forumScreenState ->
            when (forumScreenState) {
                //TODO: Implement swipe down to refresh
                ForumScreenViewModel.ForumScreenState.LOADING_COMPLETE -> {
                    Column(
                        modifier = Modifier,
                        content = {
                            LazyColumn(
                                content = {
                                    items(
                                        count = viewModel.topics.size,
                                        itemContent = { index ->
                                            val topic = viewModel.topics[index]

                                            ListItem(
                                                headlineContent = { Text(text = topic.title) },
                                                /*supportingContent = {
                                                    Text(text = topic.url)
                                                },*/
                                                trailingContent = {
                                                    IconButton(
                                                        onClick = { /*TODO*/ },
                                                        content = {
                                                            Icon(
                                                                //TODO: replace with filled icon if post is in db
                                                                imageVector = Icons.Default.BookmarkBorder,
                                                                contentDescription = stringResource(
                                                                    R.string.bookmark_topic_desc
                                                                )
                                                            )
                                                        }
                                                    )
                                                },
                                                modifier = Modifier.clickable {
                                                    navController.navigate(
                                                        Screen.ThreadScreen.withArgs(
                                                            topic.getTopicId()
                                                        )
                                                    )
                                                }
                                            )

                                            if (index < viewModel.topics.size - 1)
                                                Divider()
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .weight(0.9f)
                                    .fillMaxSize()
                            )

                            Row(
                                modifier = Modifier
                                    .weight(0.1f)
                                    .fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                    IconButton(
                                        onClick = { scope.launch { viewModel.refreshPage() } },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = stringResource(R.string.refresh_desc)
                                            )
                                        }
                                    )

                                    IconButton(
                                        onClick = { scope.launch { viewModel.goHome() } },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.Home,
                                                contentDescription = stringResource(R.string.home_desc)
                                            )
                                        }
                                    )

                                    IconButton(
                                        onClick = { viewModel.previousPage() },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.ArrowBack,
                                                contentDescription = stringResource(R.string.previous_desc)
                                            )
                                        }
                                    )

                                    IconButton(
                                        onClick = { viewModel.nextPage() },
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.ArrowForward,
                                                contentDescription = stringResource(R.string.next_desc)
                                            )
                                        }
                                    )

                                    Text(text = if (currentPage > 0) "Current page: $currentPage" else "Featured")
                                }
                            )
                        }
                    )
                }

                ForumScreenViewModel.ForumScreenState.LOADING -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = { CircularProgressIndicator() }
                    )
                }

                ForumScreenViewModel.ForumScreenState.FAILURE -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                        content = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                content = {
                                    Text(text = "Unable to get posts")
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Button(
                                        onClick = { viewModel.refreshPage() },
                                        content = { Text(text = "Retry") }
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    )
}