package com.stayspotter.model

data class FavouriteStay(
    var name: String,
    var city: String,
    var link: String,
    var photoUrl: String,
    var price: String,
    var x: Double,
    var y: Double,
    var adults: Int,
    var rooms: Int,
    var checkIn: String,
    var checkOut: String,
)