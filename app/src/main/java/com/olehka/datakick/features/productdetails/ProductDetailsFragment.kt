package com.olehka.datakick.features.productdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.olehka.datakick.InjectorUtils
import com.olehka.datakick.R
import com.olehka.datakick.databinding.FragmentProductDetailsBinding
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {

    private val args: ProductDetailsFragmentArgs by navArgs()
    private val viewModel: ProductDetailsViewModel by viewModels {
        InjectorUtils.provideProductDetailsViewModelFactory(requireContext(), args.productId)
    }
    private lateinit var binding: FragmentProductDetailsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        setUpLiveData()
        return binding.root
    }

    private fun setUpLiveData() {
        viewModel.product.observe(viewLifecycleOwner, Observer {
            binding.name.text = it.name
            binding.brand.text = it.brand
            binding.size.text = it.size
            if (it.images.isNotEmpty())
                Picasso.get()
                        .load(it.images[0].url)
                        .placeholder(R.drawable.background_no_image)
                        .into(binding.image)
        })
    }
}