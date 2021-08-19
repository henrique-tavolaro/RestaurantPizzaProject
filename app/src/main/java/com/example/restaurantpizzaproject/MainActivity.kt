package com.example.restaurantpizzaproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
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
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.ui.PizzariaViewModel
import com.example.restaurantpizzaproject.ui.navigation.Screen
import com.example.restaurantpizzaproject.ui.screens.OrderDetails
import com.example.restaurantpizzaproject.ui.screens.OrdersHistoryScreen
import com.example.restaurantpizzaproject.ui.screens.OrdersScreen
import com.example.restaurantpizzaproject.ui.screens.ProductsScreen
import com.example.restaurantpizzaproject.ui.theme.RestaurantPizzaProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

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
            val bottomBarVisibility = viewModel.bottomBarVisibility
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

                                AnimatedVisibility(visible = bottomBarVisibility.value) {
                                    BottomNavigationBar(navController = navController)
                                }
                        }
                    ) {
                        Navigation(
                            navController = navController,
                            products = products,
                            viewModel = viewModel,
                            context = this,
                            editing = editing,
                            openOrders = openOrders,
                            cancelledAndDeliveredOrders = cancelledAndDeliveredOrders,
                            bottomBarVisibility = bottomBarVisibility
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
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
    bottomBarVisibility: MutableState<Boolean>,
) {
    NavHost(navController, startDestination = Screen.OpenOrdersScreen.route) {
        composable(Screen.OpenOrdersScreen.route) {
            OrdersScreen(
                openOrders = openOrders,
                navController = navController,
                bottomBarVisibility = bottomBarVisibility,
                viewModel = viewModel,
                context = context
            )
        }
        composable(
            Screen.OrdersHistoryScreen.route) {
            OrdersHistoryScreen(
                cancelledAndDeliveredOrders = cancelledAndDeliveredOrders,
                navController = navController,
                bottomBarVisibility = bottomBarVisibility,
                viewModel = viewModel,
                context = context
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
        composable(
            Screen.OrderDetails.route,
            arguments = listOf(
                navArgument("orderId"){
                    type = NavType.StringType
                }
            )
        ) {
           OrderDetails(
               orderId = it.arguments?.getString("orderId"),
               viewModel = viewModel,
           bottomBarVisibility = bottomBarVisibility,
           context = context)
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
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon!!, contentDescription = "icon") },
                label = { Text(text = item.name!!) },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}
