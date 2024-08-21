package com.example.internwork3.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.databinding.FragmentPaymentBinding
import com.example.internwork3.databinding.FragmentResultBinding
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ResultFragment : Fragment(R.layout.fragment_result) {

    private val binding by viewBinding(FragmentResultBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            btnHome.setOnClickListener {
                findNavController().navigate(ResultFragmentDirections.actionResultFragmentToHomeFragment2())
            }
        }

    }


}