package com.example.restaurantpizzaproject.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector?, val name: String?) {

    object OpenOrdersScreen: Screen(
        route = "openOrdersScreen",
        icon = Icons.Filled.Storefront,
        name = "Abertos")

    object OrdersHistoryScreen: Screen(
        route = "ordersHistoryScreen",
        icon = Icons.Filled.AccessTime,
        name = "Histórico")

    object ProductsScreen: Screen(
        route = "productsScreen",
        icon = Icons.Filled.LocalPizza,
        name = "Produtos")

    object OrderDetails: Screen(
        route = "orderDetails/{orderId}",
        icon = null,
        name = null)

}