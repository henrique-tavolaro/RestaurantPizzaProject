package com.example.restaurantpizzaproject.di

import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.domain.interactors.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideGetProductList(
        firestore: FirestoreDatasource
    ): GetProductList {
        return GetProductList(
            firestore = firestore
        )
    }

    @ViewModelScoped
    @Provides
    fun provideAddProduct(
        firestore: FirestoreDatasource
    ): AddProduct {
        return AddProduct(
            firestore = firestore
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUpdateProduct(
        firestore: FirestoreDatasource
    ): UpdateProduct {
        return UpdateProduct(
            firestore = firestore
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetOpenOrders(
        firestore: FirestoreDatasource
    ): GetOpenOrders {
        return GetOpenOrders(
            firestore = firestore
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetCancelledAndDeliveredOrders(
        firestore: FirestoreDatasource
    ): GetCancelledAndDeliveredOrders {
        return GetCancelledAndDeliveredOrders(
            firestore = firestore
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetOrderDetails(
        firestore: FirestoreDatasource
    ): GetOrderDetails {
        return GetOrderDetails(
            firestore = firestore
        )
    }
    @ViewModelScoped
    @Provides
    fun provideUpdateOrderStatus(
        firestore: FirestoreDatasource
    ): UpdateOrderStatus {
        return UpdateOrderStatus(
            firestore = firestore
        )
    }


}