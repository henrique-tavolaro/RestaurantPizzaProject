package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FakeFirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.order1
import com.example.restaurantpizzaproject.datasource.firestore.order2
import com.example.restaurantpizzaproject.domain.models.Order
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetOrderDetailsTest{

    lateinit var firestore: FakeFirestoreDatasource

    lateinit var getOrderDetails: GetOrderDetails

    @Before
    fun setup(){
        firestore = FakeFirestoreDatasource()
        getOrderDetails = GetOrderDetails(firestore)
    }

    @Test
    fun `should get the order details and emit`() = runBlocking {
        assert(firestore.orderList.isEmpty())

        firestore.addOrder(order1, order2)

        val result = getOrderDetails.execute("id1").toList()

        assert(result[0].loading)

        val order = result[1].data
        assert(order == order1)

        assert(!result[1].loading)

    }
}