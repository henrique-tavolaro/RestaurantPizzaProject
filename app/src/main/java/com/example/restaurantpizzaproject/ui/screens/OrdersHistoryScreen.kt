package com.example.restaurantpizzaproject.ui.screens

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.example.restaurantpizzaproject.domain.interactors.GetCancelledAndDeliveredOrders
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.composables.OrderLazyColumnItem
import com.example.restaurantpizzaproject.ui.navigation.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrdersHistoryScreen(
    cancelledAndDeliveredOrders: List<Order>?,
    navController: NavController,
    bottomBarVisibility: MutableState<Boolean>,
    viewModel: PizzariaViewModel,
    context: Context,
){
    bottomBarVisibility.value = true

    LazyColumn(){
        cancelledAndDeliveredOrders?.let {
            items(items = cancelledAndDeliveredOrders){ order ->
                OrderLazyColumnItem(
                    order = order,
                navController = navController,
                viewModel = viewModel,
                context = context)
            }
        }

    }
}