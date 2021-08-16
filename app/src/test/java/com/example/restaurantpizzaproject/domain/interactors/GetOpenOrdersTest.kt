package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FakeFirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.order1
import com.example.restaurantpizzaproject.datasource.firestore.order2
import com.example.restaurantpizzaproject.domain.models.Order
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetOpenOrdersTest{

    lateinit var firestore: FakeFirestoreDatasource

    lateinit var getOpenOrders: GetOpenOrders

    @Before
    fun setup(){
        firestore = FakeFirestoreDatasource()
        getOpenOrders = GetOpenOrders(firestore)
    }

    @Test
    fun `should get a flow of Open orders`() = runBlocking{
        assert(firestore.orderList.isEmpty())

        firestore.addOrder(order1)
        firestore.addOrder(order2)

        assert(firestore.orderList.isNotEmpty())

        val list = mutableListOf<Order>()

        getOpenOrders.execute().collect {
            if (it != null) {
                for(i in it){
                    list.add(i)
                }
            }
        }
        val list2 = listOf(order1)

        assert(list == list2)
    }
}