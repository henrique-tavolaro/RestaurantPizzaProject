package com.example.restaurantpizzaproject.ui.navigation

sealed class Screen(val route: String) {

    object OpenOrdersScreen: Screen(route = "openOrdersScreen")

    object OrdersHistoryScreen: Screen(route = "ordersHistoryScreen")

    object ProductsScreen: Screen(route = "productsScreen")

}