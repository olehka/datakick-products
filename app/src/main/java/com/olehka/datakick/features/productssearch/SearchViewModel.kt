package com.olehka.datakick.features.productssearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.olehka.datakick.repository.ProductsRepository
import com.olehka.datakick.repository.model.Product

class SearchViewModel(private val repository: ProductsRepository) : ViewModel() {

    fun getProducts(query: String): LiveData<List<Product>> = repository.getProducts(query)

    fun refreshProducts(query: String) = repository.refreshProducts(query)
}