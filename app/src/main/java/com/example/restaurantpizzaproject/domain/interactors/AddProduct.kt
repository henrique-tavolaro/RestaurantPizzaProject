package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.domain.models.Product

class AddProduct(
    private val firestore: FirestoreDatasource
) {

    suspend fun execute(product: Product){
        firestore.addProduct(product)
    }

}