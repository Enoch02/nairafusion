package com.enoch02.nairafusion.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.enoch02.nairafusion.R
import com.enoch02.nairafusion.ui.screen.forum.ForumScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScaffold() {
    val scope = rememberCoroutineScope()
    var topBarTitle by rememberSaveable { mutableStateOf(R.string.app_name) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(topBarTitle)) })
        },
        content = { paddingValues ->
            ForumScreen(
                scope = scope,
                modifier = Modifier.padding(paddingValues),
                setTopBarTitle = {
                    topBarTitle = it
                }
            )
        }
    )
}