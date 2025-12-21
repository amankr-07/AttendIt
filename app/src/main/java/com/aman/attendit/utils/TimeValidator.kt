package com.aman.attendit.utils

import java.util.regex.Pattern

object TimeValidator {

    private val TIME_PATTERN = Pattern.compile(
        "^((0[1-9]|1[0-2]):[0-5][0-9])\\s?(AM|PM|am|pm)$"
    )

    fun isValid(time: String): Boolean {
        return TIME_PATTERN.matcher(time.trim()).matches()
    }
}
