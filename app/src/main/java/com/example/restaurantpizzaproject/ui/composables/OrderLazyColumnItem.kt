package com.example.restaurantpizzaproject.ui.composables


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.ui.navigation.Screen
import com.example.restaurantpizzaproject.utils.OrderStatus

@ExperimentalMaterialApi
@Composable
fun OrderLazyColumnItem(
    order: Order,
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
                .fillMaxWidth()
        ) {
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
                    text = order.date,
                    fontSize = 18.sp
                )
                Text(
                    text = order.address,
                    fontSize = 14.sp
                )
            }

        }
    }
}

