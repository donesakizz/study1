package com.example.internwork3.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.internwork3.R
import com.example.internwork3.data.model.ProductUI
import com.example.internwork3.databinding.ActivityMainBinding
import com.example.internwork3.databinding.FragmentHomeBinding
import com.example.internwork3.util.viewBinding//if you don't import this ide can't see view .Because of we make a wiewBinding Extension
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ProductAdapter.ProductListener  {

    private val binding by viewBinding(FragmentHomeBinding::bind)//viewBinding  hata vermemeesi için util extension sınıfından import edilmeli
    //if you use that coming from extensions viewBinding  bottomNvigationView mut be null and delete onCre

    //private var bottomNavigationView: BottomNavigationView? =null
    private val viewModel by viewModels<HomeViewModel>() //Even define homeState and salesState for mutableLiveData,it doesn't stop give turn led
    private val productAdapter by lazy { ProductAdapter(this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding = FragmentHomeBinding.bind(view)  // When I use this the ide does not see the bottomnavigationView

        val navController = findNavController()
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.visibility = View.VISIBLE
        //bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        //bottomNavigationView?.setVisibility(View.VISIBLE);

        with(binding){
            rvAllProducts.adapter = productAdapter //This is very important How forgotthis.Hidden recylerview and search didn't found
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId == R.id.rb_all) {
                    viewModel.getProducts()  //viewmodellarla tanımlamadan önce viewmodel'a baglı productRepository fonk,Resource retrofit return , BaseViewModel,ViewBindingExtensions(binding de lazy kullanım için)  yapılır.
                }else {
                    val category = when (checkedId) {
                        R.id.rb_notebook -> "notebook"
                        R.id.rbmntr -> "monitor"
                        R.id.rbhdst -> "headset"
                        R.id.rbcnsl -> "desktop"
                        else -> "all"
                    }
                    viewModel.getProductsByCategory(category)
                }

            }

            toolbar.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
              Toolbar.OnMenuItemClickListener{
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.action_profile -> {
                            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToProfileFragment())
                            return true
                        }
                    }
                    return false
                }
              }  )


        }//with(binding) bracetts

        viewModel.getProducts()
        //viewModel


        observeData()
    }//onViewCreated bracetts


    private fun observeData() = with(binding) {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
          when(state) {
             HomeState.Loading -> {
                 progressBar2.visibility = View.VISIBLE
             }

            is  HomeState.Data -> {
                progressBar2.visibility = View.GONE
                productAdapter.submitList(state.productsResponse)
            }

            is HomeState.Error -> {
                tvError.setText(state.throwable.message.orEmpty())
                progressBar2.visibility = View.GONE
                rvAllProducts.visibility = View.GONE
                ivError.visibility = View.VISIBLE
                tvError.visibility = View.VISIBLE
                Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
            }
              else ->{}  //IDE RECOMMEND
          }

        }


    }

    override fun onProductClick(id: Int) {
        val action = HomeFragmentDirections.actionHomeFragment2ToProductDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onFavoriteClick(product: ProductUI) {
        viewModel.addProductToFav(product)
        Snackbar.make(requireView(), "Favorilere Eklendi!", Snackbar.LENGTH_SHORT).show()
    }


}