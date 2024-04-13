package com.stayspotter.helper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Converts epoch time to a human-readable date format.
 * @param epochTimeMillis the epoch time in milliseconds
 * @return a string representing the date in the format "dd-MM-yyyy"
 */
fun convertEpochToDate(epochTimeMillis: Long): String {
    return Instant.ofEpochMilli(epochTimeMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}