/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.repository.remote

import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.utilities.PATH_ALL_PRODUCTS
import retrofit2.Call
import retrofit2.http.GET


interface WebService {

    @GET(PATH_ALL_PRODUCTS)
    fun getProducts(): Call<List<Product>>
}