package com.example.exchangeratesvk.navigation

sealed class Screen(
    val route: String
) {

    object MainScreen : Screen(ROUTE_MAIN)

    object ResultScreen : Screen(ROUTE_RESULT) {
        private const val KEY_RESULT_DATA = "key_result_data"
    }


    companion object {

        const val ROUTE_MAIN = "mainscreen"
        const val ROUTE_RESULT = "resultscreen"
    }
}