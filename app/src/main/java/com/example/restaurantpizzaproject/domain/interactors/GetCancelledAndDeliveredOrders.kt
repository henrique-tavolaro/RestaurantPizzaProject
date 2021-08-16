package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.domain.models.Order
import kotlinx.coroutines.flow.Flow

class GetCancelledAndDeliveredOrders (
    private val firestore: FirestoreDatasource
) {

    fun execute(): Flow<List<Order>?> {
        return firestore.getCancelledAndDeliveredOrders()
    }

}