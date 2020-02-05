package com.olehka.datakick.repository.local

import com.olehka.datakick.repository.model.Product

class LocalRepository(private val productsDao: ProductsDao) {

    fun getProducts(query: String = "") =
            if (query.isEmpty()) productsDao.getAllProducts()
            else productsDao.searchProducts("%$query%")

    fun saveProducts(products: Array<Product>) = productsDao.saveProducts(products)
}