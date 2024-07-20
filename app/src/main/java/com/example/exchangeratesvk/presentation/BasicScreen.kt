package com.example.exchangeratesvk.presentation

import androidx.compose.runtime.Composable
import com.example.exchangeratesvk.navigation.AppNavGraph
import com.example.exchangeratesvk.navigation.rememberNavigationState
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeScreen
import com.example.exchangeratesvk.presentation.mainscreen.ExchangeViewModel
import com.example.exchangeratesvk.presentation.resultscreen.ResultScreen

@Composable
fun BasicScreen(viewModel : ExchangeViewModel) {
    val navigationState = rememberNavigationState()

    AppNavGraph(
        navHostContoller = navigationState.navHostController,
        mainScreenContent = {
            ExchangeScreen(
                exchangeViewModel = viewModel,
                onClickListenner = { count, namewith, namein, quantity ->
                    navigationState.navigateToResult(count,namewith,namein,quantity)
                }
            )
        },
        resultScreenContent = { count, namewith, namein, quantity ->
            ResultScreen(
                count,namewith,namein,quantity,
                onBackPressed = { navigationState.navHostController.popBackStack() }
            )
        }
    )
}