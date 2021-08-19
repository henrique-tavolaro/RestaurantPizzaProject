package com.example.restaurantpizzaproject.ui.composables


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.navigation.Screen
import com.example.restaurantpizzaproject.utils.OrderStatus

@ExperimentalMaterialApi
@Composable
fun OrderLazyColumnItem(
    order: Order,
    viewModel: PizzariaViewModel,
    context: Context,
navController: NavController){
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        onClick = {

            navController.navigate("orderDetails/${order.id}")

        }

    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row() {
                Card(
                    modifier = Modifier
                        .height(60.dp)
                        .width(60.dp),
                    shape = RoundedCornerShape(50.dp),
                    elevation = 2.dp,
                    backgroundColor = if(order.status == OrderStatus.ACCEPTED) Color.Green
                    else if(order.status == OrderStatus.CANCELLEDBYCLIENT ||
                        order.status == OrderStatus.CANCELLEDBYRESTAURANT) Color.Red
                    else if(order.status == OrderStatus.DELIVERED) Color.Blue
                    else Color.White

                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = order.status[0].toString(),
                            fontSize = 24.sp)
                    }

                }
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = order.clientName,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Id: ${order.id}   Data: ${order.date}",
                        fontSize = 14.sp
                    )
                }
            }

            Button(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                enabled = order.id != OrderStatus.OPEN,
                onClick = {
                    val orderHashMap = HashMap<String, Any>()
                        orderHashMap["status"] = OrderStatus.ACCEPTED
                    viewModel.updateOrderStatus(
                        id = order.id,
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
                }
            ){
                Icon(
                    imageVector = Icons.Filled.AddTask,
                    contentDescription = "Accept order icon"
                    )
            }
        }
    }
}

