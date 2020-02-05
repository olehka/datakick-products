package com.olehka.datakick.features.productdetails

import androidx.lifecycle.ViewModel
import com.olehka.datakick.repository.ProductsRepository

class ProductDetailsViewModel(
        repository: ProductsRepository,
        productId: String
) : ViewModel() {

    val product = repository.getProduct(productId)
}