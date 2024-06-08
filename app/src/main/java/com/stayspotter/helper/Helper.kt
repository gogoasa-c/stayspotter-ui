package com.stayspotter.helper

import com.stayspotter.model.FavouriteStay
import com.stayspotter.model.Stay
import com.stayspotter.model.StayRequestDto
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

/**
 * Converts epoch time to a human-readable date format.
 * @param epochTimeMillis the epoch time in milliseconds
 * @return a string representing the date in the format "yyyy-MM-dd"
 */
fun convertEpochToDate(epochTimeMillis: Long): String {
    return Instant.ofEpochMilli(epochTimeMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}

/**
 * Converts a [Stay] object to a [FavouriteStay] object.
 * @param stay the stay object to convert
 * @param stayRequestDto the stay request DTO object
 * @return a [FavouriteStay] object
 */
fun convertStayToFavouriteStay(
    stay: Stay, stayRequestDto: StayRequestDto
): FavouriteStay {
    return FavouriteStay(
        stay.name,
        stayRequestDto.city,
        stay.link,
        stay.photoUrl,
        stay.price.split("lei")[0].trim().replace(Regex("[^\\d]"), ""),
        stay.x,
        stay.y,
        stayRequestDto.adults,
        stayRequestDto.rooms,
        stayRequestDto.checkIn,
        stayRequestDto.checkOut
    )
}

/**
 * Converts a string to a [Calendar] object.
 * @param dateAsString the date as a string
 * @return a [Calendar] object
 */
fun convertStringToCalendar(dateAsString: String): Calendar {
    val calendar = Calendar.getInstance()

    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val date = formatter.parse(dateAsString)

    calendar.time = date ?: throw RuntimeException("Failed to parse date")

    return calendar
}