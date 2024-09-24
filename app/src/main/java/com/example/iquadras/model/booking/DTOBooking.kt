package com.example.iquadras.model.booking

import com.example.iquadras.model.court.Court
import com.google.firebase.firestore.DocumentId

data class DTOBooking(
    val userId: Long? = null,
    val courtId: Long? = null,
    val date: String = "",
    val startTime: String = "",
    val durationHours: Int? = null,
)
