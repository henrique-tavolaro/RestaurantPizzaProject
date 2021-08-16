package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.domain.data.DataState
import com.example.restaurantpizzaproject.domain.models.Product
import kotlinx.coroutines.flow.Flow

class GetProductList(
    private val firestore: FirestoreDatasource
) {

    fun execute(): Flow<List<Product>?> {
        return firestore.getProducts()
    }

}