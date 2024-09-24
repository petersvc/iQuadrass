package com.example.iquadras.model.court

import com.google.firebase.firestore.DocumentId

data class Court(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val sports: List<String> = emptyList(),
    val type: String = "",
    val capacity: Int = 0,
    val score: Double = 0.0,
    val imageUrl: String = "",
    val address: String = "",
)
