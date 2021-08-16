package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FakeFirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.product1
import com.example.restaurantpizzaproject.datasource.firestore.product2
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AddProductTest{

    lateinit var firestore: FakeFirestoreDatasource

    lateinit var addProduct: AddProduct

    @Before
    fun setup(){
        firestore = FakeFirestoreDatasource()
        addProduct = AddProduct(firestore)
    }

    @Test
    fun `should add product to productsList`() = runBlocking{
        assert(firestore.productList.isEmpty())

        addProduct.execute(product1)
        addProduct.execute(product2)

        val list = listOf(product1, product2)

        assert(firestore.productList.isNotEmpty())
        assert(firestore.productList == list)


    }


}