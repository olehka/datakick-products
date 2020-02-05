package com.olehka.datakick.repository.remote


class RemoteRepository(private val webService: WebService) {

    fun getProducts(query: String = "") =
            if (query.isEmpty()) webService.getProducts()
            else webService.searchProducts(query)

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: RemoteRepository? = null

        fun getInstance(webService: WebService): RemoteRepository {
            return instance ?: synchronized(this) {
                instance ?: RemoteRepository(webService).also { instance = it }
            }
        }
    }
}