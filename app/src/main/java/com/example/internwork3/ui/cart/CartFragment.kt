package com.example.internwork3.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.data.model.ClearCartRequest
import com.example.internwork3.data.model.DeleteFromCartRequest
import com.example.internwork3.databinding.FragmentCartBinding
import com.example.internwork3.util.viewBinding
import com.example.internwork3.util.visible
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartProductsAdapter.CartProductListener {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var cartProductsAdapter: CartProductsAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var userId: String

    private var totalAmount = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth =  Firebase.auth
        userId = auth.currentUser!!.uid
        val request = ClearCartRequest(userId)

        sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        cartProductsAdapter = CartProductsAdapter(this, sharedPreferences) //

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = CartFragmentDirections.actionCartFragment2ToHomeFragment2()
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)

        viewModel.getCartProducts(userId)

        with(binding) {
            rvCartProducts.adapter = cartProductsAdapter

            //btnClear ile ilgili ve tümünü silince temizlemeyle ilgili yanlışlar var düzelt

            btnBuy.setOnClickListener {
                findNavController().navigate(CartFragmentDirections.actionCartFragment2ToPaymentFragment())
            }


        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CartState.Loading -> {
                    progressBar.visible()
                }

                is CartState.Data -> {
                    cartProductsAdapter.submitList(state.products)
                    rvCartProducts.isVisible = state.products.isNotEmpty()
                    progressBar.visibility = View.GONE

                    if (state.products.isEmpty()) {
                        rvCartProducts.visibility = View.GONE
                        tvTotal.visibility = View.GONE
                        tvAmount.visibility = View.GONE
                        btnBuy.visibility = View.GONE

                        tvError.visibility = View.VISIBLE
                        tvError.setText("Sepetinizde hiç ürün yok!")
                    }  else {
                        rvCartProducts.visibility = View.VISIBLE
                        tvTotal.visibility = View.VISIBLE
                        tvAmount.visibility = View.VISIBLE
                        btnBuy.visibility = View.VISIBLE
                        tvError.visibility = View.GONE
                    }
                }

                is CartState.Error -> {
                    progressBar.visibility = View.GONE
                }
                else ->{
                    //IDE recommend
                }
            }
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) {
            tvTotal.text = "${it} TL"
        }


    }

    override fun onProductClick(id: Int) {
        val action = CartFragmentDirections.actionCartFragment2ToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onIncreaseClick(price: Double) {
        viewModel.onIncreaseClick(price)
    }

    override fun onDecreaseClick(price: Double) {
        viewModel.onnDecreaseClick(price)
    }

    override fun onDeleteClick(id: Int, price: Double) {
        val request = DeleteFromCartRequest(id)
        viewModel.deleteProduct(request, price)
        viewModel.getCartProducts(userId)
    }


}