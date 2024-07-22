package com.example.exchangeratesvk.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route : String) {
        navHostController.navigate(route) {
            launchSingleTop = true
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
        }
    }

//    fun navigateToResult(count : String, nameWith : String, nameIn : String, quantity : String) {
//        navHostController.navigate(Screen.ResultScreen.getRouteWithArgs(count,nameWith,nameIn,quantity))
//    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) : NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}