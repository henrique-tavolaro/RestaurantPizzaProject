package com.example.restaurantpizzaproject.ui.screens

import android.opengl.Visibility
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.composables.CircularIndicator

@Composable
fun OrderDetails(
    orderId: String?,
    viewModel: PizzariaViewModel,
    bottomBarVisibility: MutableState<Boolean>
){
    bottomBarVisibility.value = false

    viewModel.getOrderDetails(orderId!!)
    val loading = viewModel.loading
    val order = viewModel.orderDetail.value

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        elevation = 4.dp
    ){

        if(order != null){
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {

                Text(
                    text = "Id do pedido: ${order.id}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Nome do cliente: ${order.clientName}",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Data: ${order.date}",
                    fontSize = 20.sp)
                Text(
                    text = "Endereço: ${order.address}",
                    fontSize = 20.sp)

                Text(
                    text = "Forma de pagamento: ${order.paymentMethod}",
                    fontSize = 20.sp)
                Text(
                    text = "Status: ${order.status}",
                    fontSize = 20.sp)
                Text(
                    text = "Valor total: ${order.totalPrice}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,)
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(

                ) {
                    item(){
                        Text(
                            text = "Resumo do pedido:",
                            fontStyle = FontStyle.Italic,
                            fontSize = 20.sp
                        )
                        Divider(
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(vertical = 2.dp)
                                .fillMaxWidth()
                        )
                    }

                    items(items = order.details) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(text = "${it.productCount}x   ${it.product}",
                                fontSize = 20.sp)
                            Text(
                                text = "R$ ${it.sumPrice}0",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularIndicator(loading = loading.value)
        }
    }
}