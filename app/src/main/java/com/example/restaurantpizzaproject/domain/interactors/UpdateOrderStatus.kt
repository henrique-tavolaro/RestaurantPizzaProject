package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource

class UpdateOrderStatus(
    private val firestore: FirestoreDatasource
) {

    suspend fun execute(
        id: String,
        orderHashMap: HashMap<String, Any>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        firestore.updateOrderStatus(id, orderHashMap, onSuccess, onFailure)
    }
}