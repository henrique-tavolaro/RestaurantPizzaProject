package com.example.restaurantpizzaproject.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.composables.LazyColumnHeader
import com.example.restaurantpizzaproject.ui.composables.ProductLazyColumnItem

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun ProductsScreen(
    products: List<Product>?,
    viewModel: PizzariaViewModel,
    context: Context,
    editing: MutableState<Product?>
) {

    val textFieldToShow = remember { mutableStateOf(Categories.PIZZAS) }

    Column() {

        val grouped = products!!.groupBy { it.categoryOrder }

        Column() {
            LazyColumnHeader(
                text = Categories.PIZZAS,
                viewModel = viewModel,
                context = context,
                textFieldToShow = textFieldToShow
            )
        }
        if (products.isNotEmpty()) {
            LazyColumn() {
                grouped.forEach { (initial, list) ->
                    if (initial == 1) {
                        items(items = list.sortedBy { it.price }) { product ->
                            ProductLazyColumnItem(
                                product = product,
                                editing = editing,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
        Column() {
            LazyColumnHeader(
                text = Categories.DESSERT,
                viewModel = viewModel,
                context = context,
                textFieldToShow = textFieldToShow
            )
        }
        if (products.isNotEmpty()) {
            LazyColumn() {
                grouped.forEach { (initial, list) ->
                    if (initial == 2) {
                        items(items = list.sortedBy { it.price }) { product ->
                            ProductLazyColumnItem(
                                product = product,
                                editing = editing,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
        Column() {
            LazyColumnHeader(
                text = Categories.DRINKS,
                viewModel = viewModel,
                context = context,
                textFieldToShow = textFieldToShow
            )
        }
        if (products.isNotEmpty()) {
            LazyColumn() {
                grouped.forEach { (initial, list) ->
                    if (initial == 3) {
                        items(items = list.sortedBy { it.price }) { product ->
                            ProductLazyColumnItem(
                                product = product,
                                editing = editing,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}




object Categories {
    const val PIZZAS = "Pizzas"
    const val DESSERT = "Sobremesas"
    const val DRINKS = "Bebidas"
}

