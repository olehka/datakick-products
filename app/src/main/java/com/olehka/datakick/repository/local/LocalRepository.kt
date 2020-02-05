package com.olehka.datakick.repository.local

import android.content.Context
import com.olehka.datakick.repository.model.Product

class LocalRepository(private val productsDao: ProductsDao) {

    fun getProducts(query: String = "") =
            if (query.isEmpty()) productsDao.getAllProducts()
            else productsDao.searchProducts("%$query%")

    fun getProduct(id: String) = productsDao.getProduct(id)

    fun saveProducts(products: Array<Product>) = productsDao.saveProducts(products)

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: LocalRepository? = null

        fun getInstance(context: Context): LocalRepository {
            return instance ?: synchronized(this) {
                instance ?: LocalRepository(
                        ProductsDatabase.instance(context.applicationContext).productsDao()
                ).also { instance = it }
            }
        }
    }
}