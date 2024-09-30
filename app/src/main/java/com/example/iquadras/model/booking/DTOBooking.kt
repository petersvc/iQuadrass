package com.example.iquadras.model.booking

data class DTOBooking(
    val userId: Long? = null,
    val courtId: Long? = null,
    val date: String = "",
    val startTime: String = "",
    val durationHours: Int? = null,
)
