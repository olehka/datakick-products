package com.olehka.datakick.features.productdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olehka.datakick.repository.ProductsRepository

class ProductDetailsViewModelFactory(
        private val repository: ProductsRepository,
        private val productId: String
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductDetailsViewModel(repository, productId) as T
    }

}