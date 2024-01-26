package com.enoch02.nairafusion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.enoch02.nairafusion.ui.screen.RootScaffold
import com.enoch02.nairafusion.ui.screen.thread.ThreadScreen

@Composable
fun NairaFusionNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.NairaFusionApp.route,
        builder = {
            composable(
                route = Screen.NairaFusionApp.route,
                content = {
                    RootScaffold(navController = navController)
                }
            )

            composable(
                route = Screen.ThreadScreen.route + "/{thread_id}",
                arguments = listOf(
                    navArgument(
                        name = "thread_id",
                        builder = { type = NavType.StringType }
                    )
                ),
                content = { entry ->
                    entry.arguments?.getString("thread_id")?.let { threadId ->
                        ThreadScreen(navController = navController, threadId = threadId)
                    }
                }
            )
        }
    )
}