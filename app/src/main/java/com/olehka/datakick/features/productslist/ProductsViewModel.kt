/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.features.productslist

import androidx.lifecycle.ViewModel
import com.olehka.datakick.repository.ProductsRepository

class ProductsViewModel(private val repository: ProductsRepository) : ViewModel() {

    private val products = repository.getProducts()

    fun getProducts() = products

    fun refreshProducts() = repository.refreshProducts()
}