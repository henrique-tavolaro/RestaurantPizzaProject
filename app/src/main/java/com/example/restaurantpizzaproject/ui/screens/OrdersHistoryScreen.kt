package com.example.restaurantpizzaproject.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.restaurantpizzaproject.domain.interactors.GetCancelledAndDeliveredOrders
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.ui.navigation.Screen

@Composable
fun OrdersHistoryScreen(
    cancelledAndDeliveredOrders: List<Order>?
){
    Text(text = "Orders History")
}