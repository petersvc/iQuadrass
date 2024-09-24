package com.example.iquadras.model.user

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val password: String = "",
    val email: String = "",
    val phone: String = "",
    val imageUrl: String = "",
)