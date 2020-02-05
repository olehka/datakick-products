package com.olehka.datakick.features.common

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olehka.datakick.R
import com.olehka.datakick.databinding.LayoutProductsItemBinding
import com.olehka.datakick.features.productslist.ProductsFragmentDirections
import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.repository.model.ProductImage
import com.olehka.datakick.utilities.DASH
import com.olehka.datakick.utilities.EMPTY
import com.olehka.datakick.utilities.UNAVAILABLE
import com.squareup.picasso.Picasso

class ProductsItemViewHolder(
        private val binding: LayoutProductsItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setClickListener(productId: String) {
        binding.root.setOnClickListener {
            val direction = ProductsFragmentDirections
                    .actionProductListFragmentToProductDetailsFragment(productId)
            it.findNavController().navigate(direction)
        }
    }

    fun render(product: Product) {
        renderProductImage(product.images)
        renderProductName(product.name ?: DASH)
        renderProductBrand(product.brand ?: EMPTY)
        renderProductSize(product.size ?: EMPTY)
    }

    private fun renderProductImage(images: List<ProductImage>) {
        if (images.isNotEmpty())
            Picasso.get()
                    .load(images[0].url)
                    .placeholder(R.drawable.background_no_image)
                    .into(binding.productsItemImageView)
    }

    private fun renderProductName(name: String) {
        binding.productsItemNameTextView.text = name
    }

    private fun renderProductBrand(brand: String) {
        binding.productsItemBrandTextView.text = itemView.context.getString(R.string.product_item_brand, if (brand.isBlank()) UNAVAILABLE else brand)
    }

    private fun renderProductSize(size: String) {
        binding.productsItemSizeTextView.text = itemView.context.getString(R.string.product_item_size, if (size.isBlank()) UNAVAILABLE else size)
    }
}