package com.olehka.datakick.repository.local

import com.olehka.datakick.repository.model.Product

class LocalRepository(private val productsDao: ProductsDao) {

    fun getProducts() = productsDao.getAllProducts()

    fun saveProducts(products: Array<Product>) = productsDao.saveProducts(products)
}