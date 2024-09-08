package com.example.iquadras.model.user

import com.example.iquadras.model.adress.Adress
import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val password: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val adress: Adress? = null
)