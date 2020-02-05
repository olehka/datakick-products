package com.olehka.datakick.repository

import androidx.lifecycle.LiveData
import com.olehka.datakick.repository.local.LocalRepository
import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.repository.remote.RemoteRepository
import com.olehka.datakick.repository.remote.WebService
import com.olehka.datakick.utilities.ConnectivityAgent
import java.util.concurrent.Executor

class ProductsRepository(
        private val localRepository: LocalRepository,
        private val remoteRepository: RemoteRepository,
        private val connectivityAgent: ConnectivityAgent,
        private val executor: Executor) {

    fun getProducts(query: String = ""): LiveData<List<Product>> {
        refreshProducts(query)
        return localRepository.getProducts(query)
    }

    fun getProduct(id: String): LiveData<Product> {
        return localRepository.getProduct(id)
    }

    fun refreshProducts(query: String = "") {
        if (!connectivityAgent.isDeviceConnectedToInternet())
            return

        executor.execute {
            val response = remoteRepository.getProducts(query).execute()
            response.body()?.toTypedArray()?.let {
                localRepository.saveProducts(it)
            }
        }
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: ProductsRepository? = null

        fun getInstance(
                localRepository: LocalRepository,
                remoteRepository: RemoteRepository,
                connectivityAgent: ConnectivityAgent,
                executor: Executor
        ): ProductsRepository {
            return instance ?: synchronized(this) {
                instance ?: ProductsRepository(
                        localRepository,
                        remoteRepository,
                        connectivityAgent,
                        executor
                ).also { instance = it }
            }
        }
    }
}