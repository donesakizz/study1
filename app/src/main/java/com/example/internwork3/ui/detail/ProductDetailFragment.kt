package com.example.internwork3.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.internwork3.R
import com.example.internwork3.data.model.AddToCartRequest
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.databinding.FragmentProductDetailBinding
import com.example.internwork3.util.loadImage
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)

    private val args by navArgs<ProductDetailFragmentArgs>()

    private val viewModel by viewModels<ProductDetailViewModel>() //without inject this class it gives red you because of mismatch type.
    //Expecting ViewModel  found: ProductDetailViewModel

    private var bottomNavigationView: BottomNavigationView? = null

    private lateinit var auth: FirebaseAuth

    var product: ProductUI? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val userId = auth.currentUser?.uid //change !! to ?

        viewModel.getProductsDetail(args.productId)

        val request = AddToCartRequest(userId!!, args.productId) //add !!

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            btnAddCart.setOnClickListener {
                viewModel.addProductToCart(request)
                Snackbar.make(requireView(), "Ürününüz Sepette !",1000).show()
            }
        }
        observeData()
    }

    private fun observeData() = with(binding)  {

        viewModel.productDetailState.observe(viewLifecycleOwner)  { state ->
            when (state) {
                ProductDetailState.Loading -> {
                    detailProgressBar.visibility = View.VISIBLE
                }

                is ProductDetailState.Data -> {
                    detailProgressBar.visibility= View.GONE
                    product = state.productResponse  //ProductResponse comes from productUI

                    if(state.productResponse !=null) {
                        tvTitle.text = state.productResponse.title
                        tvDesc.text = state.productResponse.description
                        tvCategory.text = state.productResponse.category
                        ivProduct.loadImage(state.productResponse.imageOne)
                    }
                       if(state.productResponse.saleState == true) {
                           tvDetailSalePrice.isVisible = true
                           tvDetailSalePrice.text = "${state.productResponse.salePrice} TL"
                           tvPrice.text = "${state.productResponse.price * state.productResponse.count} TL "
                           tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                       } else {
                           tvPrice.text = "${state.productResponse.price * state.productResponse.count} TL"
                           tvDetailSalePrice.isVisible = false
                       }

                }

                is ProductDetailState.Error -> {
                    detailProgressBar.visibility = View.GONE
                    Snackbar.make(requireView(), state.trowable.message.orEmpty(), 1000).show()
                }
            }
        }
    }




}