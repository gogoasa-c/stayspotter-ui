package com.stayspotter.model

import java.io.Serializable

data class UserRegisterDto(
    val username: String,
    val password: String
): Serializable