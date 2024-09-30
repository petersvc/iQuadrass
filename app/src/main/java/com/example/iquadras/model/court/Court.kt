package com.example.iquadras.model.court

data class Court(
    // @DocumentId
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val price: Double = 0.0,
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val sports: List<String> = emptyList(),
    val type: String = "",
    val capacity: Int = 0,
    val score: Double = 0.0,
    val imageUrl: String = "",
    val address: String = "",
)
