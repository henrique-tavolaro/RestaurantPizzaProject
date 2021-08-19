package com.example.restaurantpizzaproject.domain.models

data class Order(

    val id: String = "",
    val clientId: String = "",
    val clientName: String = "",
    val observation: String = "",
    val date: String = "",
    val address: String = "",
    val details: List<CartDetail> = listOf(),
    val totalPrice: Double = 0.00,
    val paymentMethod: String = "",
    val status: String = ""

)

