/*
 * Copyright (c) 2020 OlehKapustianov.
 */

package com.olehka.datakick.features.productslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.olehka.datakick.R
import com.olehka.datakick.repository.model.Product
import com.olehka.datakick.repository.model.ProductImage
import com.olehka.datakick.utilities.DASH
import com.olehka.datakick.utilities.EMPTY
import com.olehka.datakick.utilities.UNAVAILABLE
import kotlinx.android.synthetic.main.layout_products_item.view.*

class ProductsItemViewHolder(
        parent: ViewGroup,
        @LayoutRes itemViewLayoutId: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(itemViewLayoutId, parent, false)) {

    fun render(product: Product) {
        renderProductImage(product.images)
        renderProductName(product.name ?: DASH)
        renderProductBrand(product.brand ?: EMPTY)
        renderProductSize(product.size ?: EMPTY)
    }

    private fun renderProductImage(images: List<ProductImage>) {
        if (!images.isEmpty())
            Picasso.get()
                    .load(images[0].url)
                    .placeholder(R.drawable.background_no_image)
                    .into(itemView.productsItemImageView)
    }

    private fun renderProductName(name: String) {
        itemView.productsItemNameTextView.text = name
    }

    private fun renderProductBrand(brand: String) {
        itemView.productsItemBrandTextView.text = itemView.context.getString(R.string.product_item_brand, if (brand.isBlank()) UNAVAILABLE else brand)
    }

    private fun renderProductSize(size: String) {
        itemView.productsItemSizeTextView.text = itemView.context.getString(R.string.product_item_size, if (size.isBlank()) UNAVAILABLE else size)
    }
}