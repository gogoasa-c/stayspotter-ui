package com.stayspotter.model

import java.io.Serializable

data class UserStatsDto(
    var username: String,
    var numberOfSearches: Int,
    var topPercentage: Double
): Serializable