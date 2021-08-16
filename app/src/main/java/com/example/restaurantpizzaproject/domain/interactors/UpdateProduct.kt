package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource

class UpdateProduct(
    private val firestore: FirestoreDatasource
) {

    suspend fun execute(
        id: String,
        productHashMap: HashMap<String, Any>
    ){
        firestore.updateProduct(id, productHashMap)
    }
}