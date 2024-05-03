package com.stayspotter.model

import java.io.Serializable

data class UserLoginDto(
    val username: String,
    val password: String
): Serializable