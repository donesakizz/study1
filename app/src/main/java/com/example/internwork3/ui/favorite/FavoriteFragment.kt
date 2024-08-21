package com.example.internwork3.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.databinding.FragmentFavoriteBinding
import com.example.internwork3.util.viewBinding
import com.example.internwork3.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite), FavoriteAdapter.FavProductListener {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)

    private val viewModel by viewModels<FavoriteViewModel>()

    private val favoriteAdapter by lazy { FavoriteAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavProducts()

        binding.rvFavorites.adapter = favoriteAdapter

        observeData()

        val callBack = object : OnBackPressedCallback( true) {
            override fun handleOnBackPressed() {
                val action = FavoriteFragmentDirections.actionFavoriteFragment2ToHomeFragment2()
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)

    }


    private fun observeData() = with(binding) {
        viewModel.favState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavState.Loading -> {
                    progressBar.visible()
                }

                is FavState.Data -> {
                    favoriteAdapter.submitList(state.products)
                    progressBar.visibility = View.GONE
                }

                is FavState.Error -> {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }


    override fun onProductClick(id: Int) {
        val action = FavoriteFragmentDirections.actionFavoriteFragment2ToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onDeleteClick(product: ProductUI) {
        viewModel.deleteProduct(product)
    }



}