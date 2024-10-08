package com.example.internwork3.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.internwork3.R
import com.example.internwork3.databinding.FragmentProfileBinding
import com.example.internwork3.util.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {


    private val binding by viewBinding(FragmentProfileBinding::bind)
    private lateinit var auth: FirebaseAuth
    private var bottomNavigationView : BottomNavigationView? = null
    private val viewModel by viewModels<ProfileViewModel>() // red light solve with class ProfileViewModel : ViewModel()

    private val sharedPreferencesName = "MyPreferences"
    private lateinit var sharedPreferences: SharedPreferences

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  }    */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

        bottomNavigationView = getActivity()?.findViewById(R.id.bottomNavigationView);
        bottomNavigationView?.setVisibility(View.GONE);

        auth = Firebase.auth

        setUpObserves()

        binding.profileUpdateButton.setOnClickListener {
            updateUserProfile()
        }

        binding.profileSignOutButton.setOnClickListener {
            signOut()
        }

        //Fetch user details when fragment is creatred
        auth.currentUser?.let { user ->
            viewModel.fetchUserProfile(user.uid)
        }
    }

    private fun setUpObserves(){
        viewModel.userProfile.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.profileUserEmail.text = it.email
                binding.profileUserName.setText(it.name)
                binding.profileUserPhone.setText(it.phone)
            }
        }


        viewModel.updateResult.observe(viewLifecycleOwner) { result ->
            if (result) {
                Snackbar.make(requireView(), "Profile güncellendi", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(requireView(), "Profil güncelleme başarısız", Snackbar.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateUserProfile() {
        val name = binding.profileUserName.text.toString()
        val phone = binding.profileUserPhone.text.toString()
       // val address = binding.profileUserPhone.text.toString()

        if (auth.currentUser != null) {
            viewModel.updateUserProfile(auth.currentUser!!.uid, name, phone)
        }
    }

    private fun signOut() {
        auth.signOut()
        findNavController().navigate(R.id.action_profileFragment_to_signInFragment)
    }




}