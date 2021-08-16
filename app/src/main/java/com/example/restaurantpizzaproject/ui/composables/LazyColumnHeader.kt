package com.example.restaurantpizzaproject.ui.composables

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.resolveDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.screens.Categories
import java.util.*

@ExperimentalAnimationApi
@Composable
fun LazyColumnHeader(
    text: String,
    viewModel: PizzariaViewModel,
    context: Context
) {
    val textFieldVisibility = remember { mutableStateOf(false) }
    val productText = viewModel.textFieldProduct
    val priceText = viewModel.textFieldPrice

    Column {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            Color.Transparent
                        )
                    )
                )
//                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(8.dp)
            )
            IconButton(
                onClick = {
                    textFieldVisibility.value = !textFieldVisibility.value
                }) {
                if( textFieldVisibility.value)  Icon(Icons.Filled.ExpandLess, contentDescription = "Close add product icon") else
                Icon(Icons.Filled.Add, contentDescription = "Add product icon")
            }
        }
        AnimatedVisibility(visible = textFieldVisibility.value) {
            Card(
                modifier = Modifier
                    .height(60.dp),
                elevation = 4.dp
            ){
                Row(

                ) {
                    TextField(
                        value = productText.value,
                        onValueChange = viewModel::onTextFieldProduct,
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
                        onValueChange = viewModel::onTextFieldPrice,
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
                            viewModel.addProduct(
                                productText = productText,
                                priceText = priceText,
                                category = text,
                                context = context
                            )


                        }
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Check icon")
                    }
                }
            }
        }
    }
}