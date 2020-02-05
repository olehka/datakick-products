/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.features

import com.olehka.datakick.features.productslist.ProductsViewModel
import com.olehka.datakick.features.productssearch.SearchViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val productsListModule = applicationContext {
    viewModel { ProductsViewModel(get()) }
}

val productSearchModule = applicationContext {
    viewModel { SearchViewModel(get()) }
}