package com.example.restaurantpizzaproject.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantpizzaproject.domain.interactors.*
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.ui.screens.Categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PizzariaViewModel @Inject constructor(
    private val getProductList: GetProductList,
    private val addProduct: AddProduct,
    private val updateProduct: UpdateProduct,
    private val getOpenOrders: GetOpenOrders,
    private val getCancelledAndDeliveredOrders: GetCancelledAndDeliveredOrders,
    private val getOrderDetails: GetOrderDetails,
    private val updateOrderStatus: UpdateOrderStatus
) : ViewModel() {

    val textFieldProduct = mutableStateOf("")

    val textFieldPrice = mutableStateOf("")

    val bottomBarVisibility = mutableStateOf(true)

    fun onTextFieldProduct(text: String){
        textFieldProduct.value = text
    }

    fun onTextFieldPrice(text: String){
        textFieldPrice.value = text
    }

    fun getProductList(): Flow<List<Product>?> {
        return getProductList.execute()
    }

    fun getOpenOrders(): Flow<List<Order>?> {
        return getOpenOrders.execute()
    }

    fun getCancelledAndDeliveredOrders(): Flow<List<Order>?> {
        return getCancelledAndDeliveredOrders.execute()
    }

    fun addProduct(
        productText: MutableState<String>,
        priceText: MutableState<String>,
        context: Context,
        category: String

    ){
        viewModelScope.launch {

            if(productText.value.isEmpty()){
                Toast.makeText(context, "Nome do produto não preenchido", Toast.LENGTH_LONG).show()
            } else if(priceText.value.isEmpty()){
                Toast.makeText(context, "Preço do produto não preenchido", Toast.LENGTH_LONG).show()
            } else {
                val product = Product(
                    id = UUID.randomUUID().toString(),
                    name = productText.value,
                    price = priceText.value.toDouble(),
                    category = category,
                    categoryOrder = when (category) {
                        Categories.PIZZAS -> 1
                        Categories.DESSERT -> 2
                        else -> 3
                    }
                )
                addProduct.execute(product)
                productText.value = ""
                priceText.value = ""
            }

        }
    }

    fun updateProduct(id: String, productHashMap: HashMap<String, Any>){
        viewModelScope.launch {
            updateProduct.execute(id, productHashMap)
        }
    }

    fun updateOrderStatus(id: String, orderHashMap: HashMap<String, Any>){
        viewModelScope.launch {
            updateOrderStatus.execute(id, orderHashMap)
        }
    }

    val loading = mutableStateOf(false)

    val orderDetail: MutableState<Order?> = mutableStateOf(null)

    fun getOrderDetails(orderId: String){
        viewModelScope.launch {
            getOrderDetails.execute(orderId).onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let {
                    orderDetail.value = it
                    Log.d("tag1", it.toString())
                }

                dataState.error?.let {
                    Log.d("Tag", it)
                }
            }.launchIn(viewModelScope)
        }
    }

}