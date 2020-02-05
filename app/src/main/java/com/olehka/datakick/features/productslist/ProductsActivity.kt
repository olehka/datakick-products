package com.olehka.datakick.features.productslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.olehka.datakick.R
import com.olehka.datakick.databinding.ActivityProductListBinding


class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityProductListBinding>(
                this, R.layout.activity_product_list)
    }
}