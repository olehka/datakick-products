/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick

import android.app.Application
import com.olehka.datakick.features.productslist.productsListModule
import org.koin.android.ext.android.startKoin

class ProductsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(repositoryModule, productsListModule))
    }
}