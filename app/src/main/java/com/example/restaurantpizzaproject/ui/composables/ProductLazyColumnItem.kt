package com.example.restaurantpizzaproject.ui.composables

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.ui.PizzariaViewModel

@ExperimentalAnimationApi
@Composable
fun ProductLazyColumnItem(
    product: Product,
    editing: MutableState<Product?>,
    viewModel: PizzariaViewModel
) {
    val productText = remember { mutableStateOf(product.name) }
    val priceText = remember { mutableStateOf(product.price.toString()) }

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if (editing.value != product || editing.value == null) {
                            editing.value = product
                        } else {
                            editing.value = null
                        }
                    }
                )
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (editing.value != product) {
            Text(text = product.name, fontSize = 16.sp)
            Text(
                text = "R$ ${product.price}0",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(end = 8.dp)
            )
        } else {
            AnimatedVisibility(visible = editing.value == product) {
                Card(
                    modifier = Modifier
                        .height(60.dp),
                    elevation = 4.dp
                ) {
                    Row(

                    ) {
                        TextField(
                            value = productText.value,
                            onValueChange = {
                                productText.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(5f)
                                .background(Color.White),
                            placeholder = {
                                Text(text = "nome do produto")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                            )
                        )
                        TextField(
                            value = priceText.value,
                            onValueChange = {
                                priceText.value = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                                .background(Color.White),
                            placeholder = {
                                Text(text = "R$")
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                            ),

                            )
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(4.dp)
                                .weight(1f),
                            onClick = {

                                val productHashMap = HashMap<String, Any>()
                                productHashMap["name"] = productText.value
                                productHashMap["price"] = priceText.value.toDouble()
                            viewModel.updateProduct(product.id, productHashMap)
                                editing.value = null

                            }
                        ) {
                            Icon(Icons.Default.Check, contentDescription = "Check icon")
                        }
                    }
                }
            }
        }


    }
}



