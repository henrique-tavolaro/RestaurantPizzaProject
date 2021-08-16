package com.example.restaurantpizzaproject.domain.interactors

import com.example.restaurantpizzaproject.datasource.firestore.FakeFirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.product1
import com.example.restaurantpizzaproject.datasource.firestore.product2
import com.example.restaurantpizzaproject.domain.models.Product
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetProductListTest{

    lateinit var firestore: FakeFirestoreDatasource

    lateinit var getProductList: GetProductList

    @Before
    fun setup(){
        firestore = FakeFirestoreDatasource()
        getProductList = GetProductList(firestore)
    }

    @Test
    fun `should emit list of products`() = runBlocking{
        assert(firestore.productList.isEmpty())

        firestore.addProduct(product1)
        firestore.addProduct(product2)

        assert(firestore.productList.isNotEmpty())

        val list = mutableListOf<Product>()

        getProductList.execute().collect {
            if(it != null){
                for(i in it){
                    list.add(i)
                }
            }

        }

        assert(firestore.productList == list)


    }

}