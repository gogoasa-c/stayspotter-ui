package com.stayspotter.model

import java.io.Serializable

data class UserRegisterResponseDto(
    var username: String,
    var password: String
): Serializable