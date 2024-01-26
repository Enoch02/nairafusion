package com.enoch02.nairafusion.navigation

sealed class Screen(val route: String) {
    object NairaFusionApp : Screen("nairafusion_app")
    object ThreadScreen : Screen("thread_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}