package com.example.iquadras.model.booking

import com.example.iquadras.model.court.Court
import com.example.iquadras.model.user.User
import com.google.firebase.firestore.DocumentId
import java.time.LocalDateTime

data class Booking(
    @DocumentId
    val id: String = "",
    val user: User? = null,
    val court: Court? = null,
    val startTime: LocalDateTime? = null,
    val durationHours: Int = 0,
)
