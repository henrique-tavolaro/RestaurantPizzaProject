package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource

class UpdateOrderStatus(
    private val firestore: FirestoreDatasource
) {

    suspend fun execute(
        id: String,
        orderHashMap: HashMap<String, Any>
    ) {
        firestore.updateOrderStatus(id, orderHashMap)
    }
}