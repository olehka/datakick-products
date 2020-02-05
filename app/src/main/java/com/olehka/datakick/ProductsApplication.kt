package com.olehka.datakick

import android.app.Application
import com.olehka.datakick.utilities.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        buildRetrofitInstance()
    }

    private fun buildRetrofitInstance() = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}