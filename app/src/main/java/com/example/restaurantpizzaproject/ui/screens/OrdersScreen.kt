package com.example.restaurantpizzaproject.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.ui.composables.OrderLazyColumnItem

@ExperimentalMaterialApi
@Composable
fun OrdersScreen(
    openOrders: List<Order>?,
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>
) {
    bottomBarVisibility.value = true

    LazyColumn(){
        openOrders?.let {
            items(items = openOrders){ order ->
                OrderLazyColumnItem(
                    order = order,
                navController = navController)
            }
        }

    }
}