/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.features.productslist

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val productsListModule = applicationContext {
    viewModel { ProductsViewModel(get()) }
}