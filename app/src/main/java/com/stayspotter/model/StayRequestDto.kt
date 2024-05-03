package com.stayspotter.model

import java.io.Serializable

data class StayRequestDto(
    val city: String,
    val adults: Int,
    val rooms: Int,
    val checkIn: String,
    val checkOut: String
): Serializable
