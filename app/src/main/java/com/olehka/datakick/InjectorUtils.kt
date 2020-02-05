package com.olehka.datakick

import android.content.Context
import com.olehka.datakick.features.productdetails.ProductDetailsViewModelFactory
import com.olehka.datakick.features.productslist.ProductsViewModelFactory
import com.olehka.datakick.features.productssearch.SearchViewModelFactory
import com.olehka.datakick.repository.ProductsRepository
import com.olehka.datakick.repository.local.LocalRepository
import com.olehka.datakick.repository.remote.RemoteRepository
import com.olehka.datakick.repository.remote.WebClient
import com.olehka.datakick.repository.remote.WebService
import com.olehka.datakick.utilities.ConnectivityAgent
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object InjectorUtils {

    fun getWebService(): WebService = WebClient.webService

    fun getLocalRepository(context: Context): LocalRepository {
        return LocalRepository.getInstance(context)
    }

    fun getRemoteRepository(webService: WebService): RemoteRepository {
        return RemoteRepository.getInstance(webService)
    }

    fun getProductRepository(context: Context): ProductsRepository {
        return ProductsRepository.getInstance(
                getLocalRepository(context),
                getRemoteRepository(getWebService()),
                ConnectivityAgent(context),
                Executors.newSingleThreadExecutor() as Executor
        )
    }

    fun provideProductsViewModelFactory(context: Context): ProductsViewModelFactory {
        val repository = getProductRepository(context)
        return ProductsViewModelFactory(repository)
    }

    fun provideSearchViewModelFactory(context: Context): SearchViewModelFactory {
        val repository = getProductRepository(context)
        return SearchViewModelFactory(repository)
    }

    fun provideProductDetailsViewModelFactory(context: Context, productId: String):
            ProductDetailsViewModelFactory {
        val repository = getProductRepository(context)
        return ProductDetailsViewModelFactory(repository, productId)
    }
}