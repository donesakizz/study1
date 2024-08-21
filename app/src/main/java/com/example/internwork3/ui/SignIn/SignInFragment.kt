package com.example.internwork3.ui.SignIn

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.databinding.FragmentSignInBinding
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private lateinit var auth: FirebaseAuth
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        with(binding) {
            tvDontHaveAnAccount.setOnClickListener {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragmentFragment()
                findNavController().navigate(action)
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty())  {
                    signIn(email, password)
                } else {
                    Snackbar.make(requireView(), "Boş alanları doldurunuz.", 1000).show()
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        if (isValidEmail(email) && isValidPassword(password)) {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment2)
            }
                .addOnFailureListener {
                    Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
                }
        }  else {
            Snackbar.make(requireView(), "Geçersiz şifre veya mail", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun isValidPassword(password: String) : Boolean {
        return password.length >=6
    }

    private fun isValidEmail(email: String) : Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }



}