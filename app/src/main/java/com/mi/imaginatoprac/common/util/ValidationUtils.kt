package com.mi.imaginatoprac.common.util

import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

object ValidationUtils {

    val passwordREGEX = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=\\S+$)" +           //no white spaces
                ".{8,16}" +               //at least 8 characters
                "$"
    )

    fun isValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return passwordREGEX.matcher(password).matches()
    }
}
