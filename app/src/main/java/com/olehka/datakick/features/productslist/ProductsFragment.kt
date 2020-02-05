package com.olehka.datakick.features.productslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.olehka.datakick.databinding.FragmentProductListBinding
import com.olehka.datakick.features.common.EmptyProductsMessagesTimer
import com.olehka.datakick.features.common.ProductsAdapter
import com.olehka.datakick.features.common.ProductsLoadingState
import com.olehka.datakick.features.productssearch.SearchActivity
import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.InjectorUtils

class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModels {
        InjectorUtils.provideProductsViewModelFactory(requireContext())
    }
    private val adapter by lazy { ProductsAdapter() }
    private lateinit var countDownTimer: EmptyProductsMessagesTimer
    private lateinit var binding: FragmentProductListBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        setUpProductsList()
        setUpProductsListener()
        setUpFloatingActionButton()
        return binding.root
    }

    private fun setUpFloatingActionButton() {
        binding.fabSearch.setOnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        }
    }

    private fun setUpProductsList() {
        binding.productsRecyclerView.adapter = adapter
    }

    private fun setUpProductsListener() {
        viewModel.getProducts().observe(viewLifecycleOwner, Observer {
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
        countDownTimer = EmptyProductsMessagesTimer(context!!,
                { binding.productsLoadingMessageTextView.text = it },
                {
                    onProductsLoadingStateChanged(ProductsLoadingState.ERROR)
                    binding.productsInternetUnavailableButton.setOnClickListener {
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

    override fun onDestroy() {
        super.onDestroy()
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }
}