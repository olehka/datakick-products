package com.olehka.datakick.features.productssearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.olehka.datakick.R
import com.olehka.datakick.databinding.ActivityProductSearchBinding

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityProductSearchBinding>(
                this, R.layout.activity_product_search)
    }
}