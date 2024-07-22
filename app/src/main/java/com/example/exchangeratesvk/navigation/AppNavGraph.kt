package com.example.exchangeratesvk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostContoller: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    resultScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostContoller,
        startDestination = Screen.MainScreen.route,
        builder = {
            composable(Screen.MainScreen.route) {
                mainScreenContent()
            }
            composable(Screen.ResultScreen.route) {
                resultScreenContent()
            }
        }

    )
}