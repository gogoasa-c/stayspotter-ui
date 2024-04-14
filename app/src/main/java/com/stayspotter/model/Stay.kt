package com.stayspotter.model

data class Stay(
    var stayName: String,
    var foundOn: String,
    var pricePerNight: String,
    var imageUrlList: List<String>
)