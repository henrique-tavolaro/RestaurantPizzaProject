package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.domain.data.DataState
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.ui.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOrderDetails(
    private val firestore: FirestoreDatasource
) {
    suspend fun execute(orderId: String) : Flow<DataState<Order>> = flow {
        emit(DataState.loading())
        try {


            val data = firestore.getOrderDetails(orderId)

            data?.let {
                emit(DataState.success(data))
            }

        } catch (e: Exception){
            emit(DataState.error<Order>(e.message ?: "UnknownError"))
        }

    }

}