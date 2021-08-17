package com.example.restaurantpizzaproject.datasource.firestore

import com.example.restaurantpizzaproject.domain.interactors.GetProductList
import com.example.restaurantpizzaproject.domain.models.CartDetail
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.utils.OrderStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val product1 = Product("id1", "Mussarela", 50.0, "pizza")
val product2 = Product("id2", "Calabresa", 60.0, "pizza")
val order1 = Order("id1", "clientId","name","12/02/2021", "address",
    listOf(CartDetail("Mussarela", 50.0, 1)),
    50.0, "cartão", OrderStatus.OPEN
)
val order2 = Order("id1", "clientId","name","12/02/2021", "address",
    listOf(CartDetail("Mussarela", 50.0, 1)),
    50.0, "cartão", OrderStatus.CANCELLEDBYRESTAURANT
)

class FakeFirestoreDatasource(
    val productList: MutableList<Product> = mutableListOf(),
    val orderList: MutableList<Order> = mutableListOf(),
) : FirestoreDatasource {
    override fun getProducts(): Flow<List<Product>> = flow {
        emit(productList)
    }

    override suspend fun addProduct(product: Product) {
        productList.add(product)
    }

    override suspend fun updateProduct(id: String, productHashMap: HashMap<String, Any>) {
        TODO("Not yet implemented")
    }

    override fun getOpenOrders(): Flow<List<Order>?> = flow {
        emit(orderList.filter { it.status == OrderStatus.OPEN })
    }

    override fun getCancelledAndDeliveredOrders(): Flow<List<Order>?> = flow {
        emit(orderList.filter { it.status == OrderStatus.CANCELLEDBYRESTAURANT })
    }

    override suspend fun getOrderDetails(orderId: String): Order {
        return orderList.filter { it.id == orderId }[0]
    }

    override suspend fun updateOrderStatus(id: String, orderHashMap: HashMap<String, Any>) {
        TODO("Not yet implemented")
    }

    fun addOrder(order: Order){
        orderList.add(order)
    }
}