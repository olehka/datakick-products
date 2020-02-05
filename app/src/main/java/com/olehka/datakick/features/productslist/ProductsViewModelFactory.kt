package com.olehka.datakick.features.productslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olehka.datakick.repository.ProductsRepository

class ProductsViewModelFactory(
        private val repository: ProductsRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductsViewModel(repository) as T
    }

}