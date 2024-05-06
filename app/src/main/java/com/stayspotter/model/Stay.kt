package com.stayspotter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Stay(
//    var stayName: String,
//    var foundOn: String,
//    var pricePerNight: String,
//    var imageUrlList: List<String>
    var name: String,
    var link: String,
    var photo: String,
    var price: String,
    var x: Double,
    var y: Double
) : Serializable, Parcelable