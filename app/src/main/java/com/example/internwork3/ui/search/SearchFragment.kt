package com.example.internwork3.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.databinding.FragmentSearchBinding
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.SearchProductListener {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter by lazy { SearchAdapter(this) }

    private var bottomNavigationView: BottomNavigationView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.VISIBLE);


        with(binding) {
            rvSearch.adapter = searchAdapter
            //(searchView as androidx.appcompat.widget.SearchView) :  It fixed the error in the ide! but could not prevent the crush.
            // Reason: It was necessary to define searchview with androidx.widget in the layout.
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let { query ->
                        if (query.length >= 2) {
                            viewModel.getSearchProduct(query)
                        } else {
                            Snackbar.make(
                                requireView(),
                                "Aramak istediğiniz ürün için 2 karakter  girilmeli ",
                                1000
                            ).show()
                        }
                    }
                    return true
                }
            })

        }


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = SearchFragmentDirections.actionSearchFragment2ToHomeFragment2()
                findNavController().navigate(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Loading -> {
                    progressBar2.visibility = View.VISIBLE
                    bottomNavigationView?.setVisibility(View.GONE);

                }

                is SearchState.Data -> {
                    progressBar2.visibility = View.GONE
                    searchAdapter.submitList(state.products)
                    bottomNavigationView?.setVisibility(View.GONE);
                }

                is SearchState.Error -> {
                    progressBar2.visibility = View.GONE

                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragment2ToProductDetailFragment(id)
        findNavController().navigate(action)
    }
}

