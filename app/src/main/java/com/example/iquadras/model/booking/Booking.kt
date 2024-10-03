package com.example.iquadras.model.booking

import com.example.iquadras.model.court.Court
import com.example.iquadras.model.user.User
import com.google.firebase.firestore.DocumentId

data class Booking(
    //@DocumentId
    val id: Long? = null,
    val user: User? = null,
    val court: Court? = null,
    val date: String = "",
    val startTime: String = "",
    val durationHours: Int? = null,
)
