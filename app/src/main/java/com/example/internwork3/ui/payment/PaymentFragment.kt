package com.example.internwork3.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.databinding.FragmentPaymentBinding
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            btnPay.setOnClickListener {
                val address = etAddress.text.toString()
                val name = etNameSurname.text.toString()
                val cardNumber = etCardNumber.text.toString()
                val cardDate = etCardDate.text.toString()
                val cvc = etCvc.text.toString()

                if (cardNumber.length ==16 && cardDate.isNotEmpty() && cvc.isNotEmpty() && address.isNotEmpty() && name.isNotEmpty()) {
                    findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToResultFragment())
                } else {
                    Snackbar.make(requireView(), "Tüm alanlar doldurulmalı kart numarası 16 sayılı olmalı", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }



}