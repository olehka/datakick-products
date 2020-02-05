package com.olehka.datakick.features.productssearch

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.olehka.datakick.InjectorUtils
import com.olehka.datakick.databinding.FragmentProductSearchBinding
import com.olehka.datakick.features.common.EmptyProductsMessagesTimer
import com.olehka.datakick.features.common.ProductsAdapter
import com.olehka.datakick.features.common.ProductsLoadingState
import com.olehka.datakick.repository.model.Product

class SearchFragment: Fragment() {

    private lateinit var binding: FragmentProductSearchBinding
    private val viewModel: SearchViewModel by viewModels {
        InjectorUtils.provideSearchViewModelFactory(requireContext())
    }
    private val adapter by lazy { ProductsAdapter{ productId ->
        navigateToDetailsScreen(productId)
    } }
    private lateinit var countDownTimer: EmptyProductsMessagesTimer

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductSearchBinding.inflate(inflater, container, false)
        setUpProductsList()
        setUpSearchInputListener()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        checkInputSearch()
    }

    private fun setUpSearchInputListener() {
        binding.inputSearch.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.inputSearch.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun checkInputSearch() {
        val query = binding.inputSearch.text.toString()
        if (query.isNotEmpty()) {
            observeProducts(query)
        }
    }

    private fun doSearch(v: View) {
        val query = binding.inputSearch.text.toString()
        dismissKeyboard(v.windowToken)
        observeProducts(query)
    }

    private fun observeProducts(query: String) {
        viewModel.getProducts(query).observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty())
                onProductsListEmpty()
            else
                displayProducts(it)
        })
    }

    private fun setUpProductsList() {
        binding.productsRecyclerView.adapter = adapter
    }

    private fun onProductsListEmpty() {
        onProductsLoadingStateChanged(ProductsLoadingState.LOADING)
        setUpLoadingMessage()
    }

    private fun setUpLoadingMessage() {
        countDownTimer = EmptyProductsMessagesTimer(requireContext(),
                { binding.productsLoadingMessageTextView.text = it },
                {
                    onProductsLoadingStateChanged(ProductsLoadingState.ERROR)
                    binding.productsInternetUnavailableButton.setOnClickListener {
                        val query = binding.inputSearch.text.toString()
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
                binding.productsLoadingMessageTextView.visibility = View.VISIBLE
                binding.productsInternetUnavailableContainer.visibility = View.GONE
                binding.productsRecyclerView.visibility = View.GONE
            }
            ProductsLoadingState.ERROR -> {
                binding.productsInternetUnavailableContainer.visibility = View.VISIBLE
                binding.productsLoadingMessageTextView.visibility = View.GONE
                binding.productsRecyclerView.visibility = View.GONE
            }
            ProductsLoadingState.LOADED -> {
                binding.productsRecyclerView.visibility = View.VISIBLE
                binding.productsLoadingMessageTextView.visibility = View.GONE
                binding.productsInternetUnavailableContainer.visibility = View.GONE
            }
        }
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun navigateToDetailsScreen(productId: String) {
        val direction = SearchFragmentDirections
                .actionProductSearchFragmentToProductDetailsFragment(productId)
        requireView().findNavController().navigate(direction)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }
}