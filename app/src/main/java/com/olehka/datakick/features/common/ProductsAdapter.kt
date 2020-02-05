package com.olehka.datakick.features.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.olehka.datakick.R
import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.repository.model.ProductDiffUtilCallback

class ProductsAdapter(
        private val callback: (id: String) -> Unit
) : RecyclerView.Adapter<ProductsItemViewHolder>() {

    private val products: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductsItemViewHolder(
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_products_item, parent, false
            )
    )

    override fun onBindViewHolder(holder: ProductsItemViewHolder, position: Int) {
        holder.render(products[position])
        holder.setClickListener(products[position].id, callback)
    }

    override fun getItemCount() = products.size

    override fun getItemViewType(position: Int) = position

    fun updateProducts(products: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(this.products, products))
        diffResult.dispatchUpdatesTo(this)
        this.products.clear()
        this.products.addAll(products)
    }
}