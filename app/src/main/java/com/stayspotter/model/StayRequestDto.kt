package com.stayspotter.model

import java.io.Serializable

data class StayRequestDto(
    var city: String = "",
    var adults: Int = 1,
    var rooms: Int = 1,
    var checkIn: String = "",
    var checkOut: String = "",
    var priceRangeStart: Int = 0,
    var priceRangeEnd: Int = 1000,
): Serializable
