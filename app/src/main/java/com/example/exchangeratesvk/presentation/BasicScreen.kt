package com.example.exchangeratesvk.presentation

import androidx.compose.runtime.Composable
import com.example.exchangeratesvk.navigation.AppNavGraph
import com.example.exchangeratesvk.navigation.Screen
import com.example.exchangeratesvk.navigation.rememberNavigationState
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeScreen
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeViewModel
import com.example.exchangeratesvk.presentation.resultscreen.ResultScreen

@Composable
fun BasicScreen(viewModel: ExchangeViewModel) {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostContoller = navigationState.navHostController,
        mainScreenContent = {
            ExchangeScreen(
                exchangeViewModel = viewModel,
                onConvertClick = {
                    navigationState.navigateTo(Screen.ROUTE_RESULT)
                }
            )
        },
        resultScreenContent = {
            ResultScreen(
                viewModel = viewModel,
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        }
    )
}