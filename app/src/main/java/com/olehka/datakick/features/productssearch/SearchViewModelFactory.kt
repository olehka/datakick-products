package com.olehka.datakick.features.productssearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.olehka.datakick.repository.ProductsRepository

class SearchViewModelFactory(
        private val repository: ProductsRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }

}