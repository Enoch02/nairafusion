package com.enoch02.nairafusion.ui.screen.thread

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.enoch02.nairafusion.R
import com.enoch02.nairafusion.ui.screen.thread.components.ThreadItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadScreen(
    navController: NavHostController,
    threadId: String,
    viewModel: ThreadScreenViewModel = viewModel()
) {
    //TODO: might replace with a better method
    //TODO: add loading animation
    LaunchedEffect(key1 = Unit, block = { viewModel.getThread(threadId) })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.navigate_back_desc)
                            )
                        }
                    )
                }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                content = {
                    items(
                        count = viewModel.threadItems.size,
                        itemContent = { index ->
                            val item = viewModel.threadItems[index]

                            ThreadItem(
                                title = item.title,
                                user = item.user,
                                dateCreated = item.dateCreated,
                                content = item.content,
                                likes = item.likes,
                                attachmentImages = item.attachmentImageUrls
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    )
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(4.dp)
            )
        }
    )
}