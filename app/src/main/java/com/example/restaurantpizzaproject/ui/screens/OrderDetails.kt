package com.example.restaurantpizzaproject.ui.screens

import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.composables.CircularIndicator
import com.example.restaurantpizzaproject.utils.OrderStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OrderDetails(
    orderId: String?,
    viewModel: PizzariaViewModel,
    bottomBarVisibility: MutableState<Boolean>,
    context: Context
) {
    bottomBarVisibility.value = false
    viewModel.getOrderDetails(orderId!!)
    val loading = viewModel.loading
    val order = viewModel.orderDetail.value

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        elevation = 4.dp
    ) {

        if (order != null) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {

                Text(
                    text = "Id do pedido: ${order.id}",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Nome do cliente: ${order.clientName}",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Data: ${order.date}",
                    fontSize = 18.sp
                )
                Text(
                    text = "Endereço: ${order.address}",
                    fontSize = 18.sp
                )

                Text(
                    text = "Forma de pagamento: ${order.paymentMethod}",
                    fontSize = 18.sp
                )
                Text(
                    text = "Observações: ${order.observation}",
                    fontSize = 18.sp
                )
                Text(
                    text = "Status: ${order.status}",
                    fontSize = 18.sp
                )
                Text(
                    text = "Valor total: ${order.totalPrice}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 2.dp),
                        enabled = order.status == OrderStatus.OPEN || order.status == OrderStatus.ACCEPTED,
                        onClick = {
                            val orderHashMap = HashMap<String, Any>()
                            orderHashMap["status"] = OrderStatus.CANCELLEDBYRESTAURANT
                            viewModel.updateOrderStatus(
                                id = orderId,
                                orderHashMap = orderHashMap,
                                onSuccess = {
                                    Toast.makeText(context, "Pedido cancelado", Toast.LENGTH_LONG)
                                        .show()
                                },
                                onFailure = {
                                    Toast.makeText(
                                        context,
                                        "Erro ao cancelar pedido",
                                        Toast.LENGTH_LONG
                                    ).show()
                                })
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(text = "CANCELAR")
                    }
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 2.dp),
                        enabled = order.status == OrderStatus.OPEN || order.status == OrderStatus.ACCEPTED,
                        onClick = {
                            val orderHashMap = HashMap<String, Any>()

                           if(order.status == OrderStatus.OPEN){
                               orderHashMap["status"] = OrderStatus.ACCEPTED
                               viewModel.updateOrderStatus(
                                   id = orderId,
                                   orderHashMap = orderHashMap,
                                   onSuccess = {
                                       Toast.makeText(context, "Preparando pedido", Toast.LENGTH_LONG)
                                           .show()
                                   },
                                   onFailure = {
                                       Toast.makeText(
                                           context,
                                           "Erro ao aceitar pedido",
                                           Toast.LENGTH_LONG
                                       ).show()
                                   })
                           } else {
                               orderHashMap["status"] = OrderStatus.DELIVERED
                               viewModel.updateOrderStatus(
                                   id = orderId,
                                   orderHashMap = orderHashMap,
                                   onSuccess = {
                                       Toast.makeText(context, "Pedido entregue", Toast.LENGTH_LONG)
                                           .show()
                                   },
                                   onFailure = {
                                       Toast.makeText(
                                           context,
                                           "Erro ao marcar pedido como entregue",
                                           Toast.LENGTH_LONG
                                       ).show()
                                   })
                           }
                        },
                        colors = ButtonDefaults
                            .buttonColors(
                                backgroundColor = if (
                                    order.status == OrderStatus.OPEN) Color(0xff1c802d)
                                else Color.Blue
                            )
                    ) {
                        Text(text = if (order.status == OrderStatus.OPEN) "ACEITAR PEDIDO" else "ENTREGUE",
                        color = Color.White)
                    }
                }

                LazyColumn(

                ) {
                    item() {
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

                            Text(
                                text = "${it.productCount}x   ${it.product}",
                                fontSize = 18.sp
                            )
                            Text(
                                text = "R$ ${it.sumPrice}0",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
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