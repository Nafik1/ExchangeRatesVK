package com.example.exchangeratesvk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostContoller: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    resultScreenContent: @Composable (String,String,String,String) -> Unit
) {
    NavHost(
        navController = navHostContoller,
        startDestination = Screen.MainScreen.route,
        builder = {
            composable(Screen.MainScreen.route) {
                mainScreenContent()
            }
            composable(Screen.ResultScreen.route) {
                val count = it.arguments?.getString(Screen.KEY_COUNT_RATE) ?: ""
                val nameWith = it.arguments?.getString(Screen.KEY_NAMEWITH_RATE) ?: ""
                val nameIn = it.arguments?.getString(Screen.KEY_NAMEIN_RATE) ?: ""
                val quantity = it.arguments?.getString(Screen.KEY_QUANTITY_RATE) ?: ""
                resultScreenContent(count,nameWith,nameIn,quantity)
            }
        }

    )
}