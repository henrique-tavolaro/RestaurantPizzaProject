package com.example.restaurantpizzaproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.navigation.Screen
import com.example.restaurantpizzaproject.ui.screens.OrdersHistoryScreen
import com.example.restaurantpizzaproject.ui.screens.OrdersScreen
import com.example.restaurantpizzaproject.ui.screens.ProductsScreen
import com.example.restaurantpizzaproject.ui.theme.RestaurantPizzaProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PizzariaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val editing = mutableStateOf<Product?>(null)

            val products = viewModel.getProductList().collectAsState(initial = null).value
            val openOrders = viewModel.getOpenOrders().collectAsState(initial = null).value
            val cancelledAndDeliveredOrders = viewModel.getCancelledAndDeliveredOrders().collectAsState(initial = null).value

            RestaurantPizzaProjectTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Pizzaria Mario & Luigi")
                                }
                            )
                        },
                        bottomBar = {

                                BottomNavigationBar(navController = navController)

                        }
                    ) {
                        Navigation(
                            navController = navController,
                            products = products,
                            viewModel = viewModel,
                            context = this,
                            editing = editing,
                            openOrders = openOrders,
                            cancelledAndDeliveredOrders = cancelledAndDeliveredOrders
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun Navigation(
    navController: NavHostController,
    products: List<Product>?,
    viewModel: PizzariaViewModel,
    context: Context,
    editing: MutableState<Product?>,
    openOrders: List<Order>?,
    cancelledAndDeliveredOrders: List<Order>?,
) {
    NavHost(navController, startDestination = Screen.OpenOrdersScreen.route) {
        composable(Screen.OpenOrdersScreen.route) {
            OrdersScreen(
                openOrders = openOrders
            )
        }
        composable(Screen.OrdersHistoryScreen.route) {
            OrdersHistoryScreen(
                cancelledAndDeliveredOrders = cancelledAndDeliveredOrders
            )
        }
        composable(Screen.ProductsScreen.route) {
            ProductsScreen(
                products = products,
                viewModel = viewModel,
                context = context,
                editing = editing
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        Screen.OpenOrdersScreen,
        Screen.OrdersHistoryScreen,
        Screen.ProductsScreen,
    )
    BottomNavigation(
//        backgroundColor = colorResource(id = MaterialTheme.colors.primary),
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = Icons.Filled.ProductionQuantityLimits, contentDescription = "icon") },
                label = { Text(text = item.route) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
