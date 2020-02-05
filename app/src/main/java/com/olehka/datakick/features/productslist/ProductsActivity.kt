package com.olehka.datakick.features.productslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.olehka.datakick.R
import com.olehka.datakick.features.common.EmptyProductsMessagesTimer
import com.olehka.datakick.features.common.ProductsAdapter
import com.olehka.datakick.features.common.ProductsLoadingState
import com.olehka.datakick.features.productssearch.SearchActivity
import com.olehka.datakick.repository.model.Product
import kotlinx.android.synthetic.main.activity_products.*
import org.koin.android.architecture.ext.viewModel

class ProductsActivity : AppCompatActivity() {

    private val viewModel by viewModel<ProductsViewModel>()
    private val adapter by lazy { ProductsAdapter() }
    private lateinit var countDownTimer: EmptyProductsMessagesTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        setUpProductsList()
        setUpProductsListener()
        setUpFloatingActionButton()
    }

    private fun setUpFloatingActionButton() {
        fabSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun setUpProductsList() {
        productsRecyclerView.adapter = adapter
    }

    private fun setUpProductsListener() {
        viewModel.getProducts().observe(this, Observer {
            if (it.isNullOrEmpty())
                onProductsListEmpty()
            else
                displayProducts(it)
        })
    }

    private fun onProductsListEmpty() {
        onProductsLoadingStateChanged(ProductsLoadingState.LOADING)
        setUpLoadingMessage()
    }

    private fun setUpLoadingMessage() {
        countDownTimer = EmptyProductsMessagesTimer(this,
                { productsLoadingMessageTextView.text = it },
                {
                    onProductsLoadingStateChanged(ProductsLoadingState.ERROR)
                    productsInternetUnavailableButton.setOnClickListener {
                        viewModel.refreshProducts()
                        onProductsListEmpty()
                    }
                }
        )
        countDownTimer.start()
    }

    private fun displayProducts(products: List<Product>) {
        onProductsLoadingStateChanged(ProductsLoadingState.LOADED)
        adapter.updateProducts(products)
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }

    private fun onProductsLoadingStateChanged(state: ProductsLoadingState) {
        when (state) {
            ProductsLoadingState.LOADING -> {
                productsLoadingMessageTextView.visibility = View.VISIBLE
                productsInternetUnavailableContainer.visibility = View.GONE
                productsRecyclerView.visibility = View.GONE
            }
            ProductsLoadingState.ERROR -> {
                productsInternetUnavailableContainer.visibility = View.VISIBLE
                productsLoadingMessageTextView.visibility = View.GONE
                productsRecyclerView.visibility = View.GONE
            }
            ProductsLoadingState.LOADED -> {
                productsRecyclerView.visibility = View.VISIBLE
                productsLoadingMessageTextView.visibility = View.GONE
                productsInternetUnavailableContainer.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }
}