package com.example.restaurantpizzaproject.di


import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasource
import com.example.restaurantpizzaproject.datasource.firestore.FirestoreDatasourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirestoreDatasource() : FirestoreDatasource {
        return FirestoreDatasourceImpl(provideFirestore())
    }

}