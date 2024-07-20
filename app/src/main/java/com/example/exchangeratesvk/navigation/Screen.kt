package com.example.exchangeratesvk.navigation

sealed class Screen(
    val route: String
) {

    object MainScreen : Screen(ROUTE_MAIN)

    object ResultScreen : Screen(ROUTE_RESULT) {

        private const val ROUTE_FOR_ARGS = "resultscreen"

        fun getRouteWithArgs(
            count: String,
            nameWith: String,
            nameIn: String,
            quantityRate : String
        ): String {
            return "$ROUTE_FOR_ARGS/${count}/${nameWith}/${nameIn}/${quantityRate}"
        }
    }


    companion object {

        const val KEY_COUNT_RATE = "count_rate"
        const val KEY_NAMEWITH_RATE = "namewith_rate"
        const val KEY_NAMEIN_RATE = "namein_rate"
        const val KEY_QUANTITY_RATE = "quantity_rate"

        const val ROUTE_MAIN = "mainscreen"
        const val ROUTE_RESULT =
            "resultscreen/{$KEY_COUNT_RATE}/{$KEY_NAMEWITH_RATE}/{$KEY_NAMEIN_RATE}/{$KEY_QUANTITY_RATE}"
    }
}