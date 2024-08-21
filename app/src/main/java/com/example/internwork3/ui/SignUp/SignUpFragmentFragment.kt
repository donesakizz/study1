package com.example.internwork3.ui.SignUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.databinding.FragmentSignUpFragmentBinding
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragmentFragment : Fragment(R.layout.fragment_sign_up_fragment) {

    private val binding by viewBinding(FragmentSignUpFragmentBinding::bind)
    private lateinit var auth: FirebaseAuth
    private var bottomNavigationView: BottomNavigationView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.let { //you use : instead of .
            findNavController().navigate(R.id.action_signUpFragmentFragment_to_homeFragment2)
        }

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            btnSignup.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    signUp(email, password)
                } else {
                    Snackbar.make(requireView(), "Alanlar boş bırakılamaz" ,1000).show()
                }
            }

            tvHaveAnAccount.setOnClickListener {
                val action = SignUpFragmentFragmentDirections.actionSignUpFragmentFragmentToHomeFragment2()
                findNavController().navigate(action)
            }
        }
    }



    private fun signUp(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                findNavController().navigate(R.id.action_signUpFragmentFragment_to_homeFragment2)
            }
            .addOnFailureListener {
                Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
            }
    } else {
            Snackbar.make(requireView(), "Şifre ya da mail geçersiz", Snackbar.LENGTH_SHORT).show()

        }
    }

    private fun isValidEmail(email: String) : Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >=6
    }




}