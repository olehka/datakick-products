package com.olehka.datakick

import android.app.Application
import com.olehka.datakick.features.productSearchModule
import com.olehka.datakick.features.productsListModule
import org.koin.android.ext.android.startKoin

class ProductsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(repositoryModule, productsListModule, productSearchModule))
    }
}