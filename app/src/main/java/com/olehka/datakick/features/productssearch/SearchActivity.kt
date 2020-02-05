package com.olehka.datakick.features.productssearch

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.olehka.datakick.R
import com.olehka.datakick.features.common.EmptyProductsMessagesTimer
import com.olehka.datakick.features.common.ProductsAdapter
import com.olehka.datakick.features.common.ProductsLoadingState
import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.InjectorUtils
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModels {
        InjectorUtils.provideSearchViewModelFactory(this)
    }
    private val adapter by lazy { ProductsAdapter() }
    private lateinit var countDownTimer: EmptyProductsMessagesTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setUpProductsList()
        setUpSearchInputListener()
        checkInputSearch(savedInstanceState)
    }

    private fun setUpSearchInputListener() {
        inputSearch.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        inputSearch.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun checkInputSearch(savedInstanceState: Bundle?) {
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_INPUT_SEARCH)) {
            val query = savedInstanceState.getString(KEY_INPUT_SEARCH)
            if (query.isNullOrEmpty()) {
                return
            }
            Log.d("SearchActivity", "checkInputSearch: $query")
            observeProducts(query)
        }
    }

    private fun doSearch(v: View) {
        val query = inputSearch.text.toString()
        dismissKeyboard(v.windowToken)
        observeProducts(query)
    }

    private fun observeProducts(query: String) {
        viewModel.getProducts(query).observe(this, Observer {
            if (it.isNullOrEmpty())
                onProductsListEmpty()
            else
                displayProducts(it)
        })
    }

    private fun setUpProductsList() {
        productsRecyclerView.adapter = adapter
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
                        val query = inputSearch.text.toString()
                        viewModel.refreshProducts(query)
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

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_INPUT_SEARCH, inputSearch.text.toString())
    }

    companion object {
        const val KEY_INPUT_SEARCH = "key_input_search"
    }
}