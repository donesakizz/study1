package com.example.internwork3.data.model

import android.provider.ContactsContract.CommonDataKinds.Email

data class UserProfile(
    val name: String = "",
    val email: String ="",
    val phone: String ="",
    val adress: String =""
)

//Just model isn't related with api in this project, this model
