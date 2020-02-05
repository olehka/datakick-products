package com.olehka.datakick.repository.remote

class RemoteRepository(private val webService: WebService) {

    fun getProducts(query: String = "") =
            if (query.isEmpty()) webService.getProducts()
            else webService.searchProducts(query)
}