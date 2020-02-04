/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.repository.remote

class RemoteRepository(private val webService: WebService) {

    fun getProducts() = webService.getProducts()
}