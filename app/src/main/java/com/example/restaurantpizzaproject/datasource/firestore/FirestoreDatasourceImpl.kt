package com.example.restaurantpizzaproject.datasource.firestore

import android.util.Log
import com.example.restaurantpizzaproject.domain.models.Order
import com.example.restaurantpizzaproject.domain.models.Product
import com.example.restaurantpizzaproject.utils.OrderStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

const val PRODUCTS = "Product"
const val ORDERS = "Orders"
const val STATUS = "status"

@ExperimentalCoroutinesApi
class FirestoreDatasourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreDatasource {

    override fun getProducts(): Flow<List<Product>?> = callbackFlow {
        val collection = firestore
            .collection(PRODUCTS)
            .orderBy("categoryOrder")

        val snapshotListener = collection.addSnapshotListener() { snapshot, _ ->
            this.trySend(snapshot!!.toObjects(Product::class.java)).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addProduct(product: Product) {
        firestore
            .collection(PRODUCTS)
            .document(product.id)
            .set(product, SetOptions.merge())
    }



    override suspend fun updateProduct(id: String, productHashMap: HashMap<String, Any>) {
        firestore
            .collection(PRODUCTS)
            .document(id)
            .set(productHashMap, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("TAG1", "success")
            }
            .addOnFailureListener{
                Log.d("TAG1", "failure")
            }
    }

    override fun getOpenOrders(): Flow<List<Order>?> = callbackFlow{
        val collection = firestore
            .collection(ORDERS)
            .whereIn(STATUS, listOf(OrderStatus.OPEN, OrderStatus.ACCEPTED))

        val snapshotListener = collection.addSnapshotListener() { snapshot, _ ->
            this.trySend(snapshot!!.toObjects(Order::class.java)).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }

    }

    override fun getCancelledAndDeliveredOrders(): Flow<List<Order>?> = callbackFlow {
        val collection = firestore
            .collection(ORDERS)
            .whereIn(STATUS, listOf(OrderStatus.CANCELLEDBYCLIENT, OrderStatus.CANCELLEDBYRESTAURANT, OrderStatus.DELIVERED))

        val snapshotListener = collection.addSnapshotListener() { snapshot, _ ->
            this.trySend(snapshot!!.toObjects(Order::class.java)).isSuccess
        }

            awaitClose {
                snapshotListener.remove()
            }
    }

    override suspend fun getOrderDetails(orderId: String) : Order? {
        return firestore
            .collection(ORDERS)
            .document(orderId)
            .get()
            .await()
            .toObject(Order::class.java)
    }

    override suspend fun updateOrderStatus(
        id: String,
        orderHashMap: HashMap<String, Any>,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        firestore
            .collection(ORDERS)
            .document(id)
            .set(orderHashMap, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("TAGA", "foi")
                onSuccess()
            }
            .addOnFailureListener{

                Log.d("TAGA", "naõ foi")
                onFailure()
            }
    }

}

