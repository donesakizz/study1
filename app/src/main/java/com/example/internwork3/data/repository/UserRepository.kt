package com.example.internwork3.data.repository

import android.adservices.adid.AdId
import com.example.internwork3.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getUserProfile(userId:String) : UserProfile? {
        val docRef = firestore.collection("users").document(userId)
        val snapshot = docRef.get().await() //await turn red so I make this function suspend
        return snapshot.toObject(UserProfile::class.java)
    }


    suspend fun updateUserProfile(userId: String, name: String, phone: String): Boolean{
        return try {
            val userProfile = mapOf(
                "name" to name,
                "phone" to phone,
            )
            firestore.collection("users").document(userId).set(userProfile, SetOptions.merge()).await()
            true
        } catch (e: Exception) {
            false
        }
    }


}