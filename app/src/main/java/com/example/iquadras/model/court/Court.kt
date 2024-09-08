package com.example.iquadras.model.court

import com.google.firebase.firestore.DocumentId

data class Court(

    @DocumentId
    val id: String = "",
    val name: String = "",
    val adress: String = "",
    val phone: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val sports: List<String> = emptyList(),
    val type: String = "", // beach, court, field...
    val capacity: Int = 0,
    val score: Double = 0.0,
)
