package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FakeFirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.order1
import com.example.restaurantpizzaproject.datasource.firestore.order2
import com.example.restaurantpizzaproject.domain.models.Order
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCancelledAndDeliveredOrdersTest{


    lateinit var firestore: FakeFirestoreDatasource

    lateinit var getCancelledAndDeliveredOrders: GetCancelledAndDeliveredOrders

    @Before
    fun setup(){
        firestore = FakeFirestoreDatasource()
        getCancelledAndDeliveredOrders = GetCancelledAndDeliveredOrders(firestore)
    }

    @Test
    fun `should get a flow of Cancelled orders`() = runBlocking{
        assert(firestore.orderList.isEmpty())

        firestore.addOrder(order1)
        firestore.addOrder(order2)

        assert(firestore.orderList.isNotEmpty())

        val list = mutableListOf<Order>()

        getCancelledAndDeliveredOrders.execute().collect {
            if (it != null) {
                for(i in it){
                    list.add(i)
                }
            }
        }
        val list2 = listOf(order2)

        assert(list == list2)
    }

}