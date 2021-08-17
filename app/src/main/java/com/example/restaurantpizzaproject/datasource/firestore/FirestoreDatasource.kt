package com.example.restaurantpizzaproject.datasource.firestore

import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface FirestoreDatasource {

    fun getProducts() : Flow<List<Product>?>

    suspend fun addProduct(product: Product)

    suspend fun updateProduct(id: String, productHashMap: HashMap<String, Any>)

    fun getOpenOrders() : Flow<List<Order>?>

    fun getCancelledAndDeliveredOrders() : Flow<List<Order>?>

    suspend fun getOrderDetails(orderId: String) : Order?

    suspend fun updateOrderStatus(id: String, orderHashMap: HashMap<String, Any>)

}