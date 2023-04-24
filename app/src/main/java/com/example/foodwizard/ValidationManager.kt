package com.example.foodwizard

import java.security.MessageDigest

object ValidationManager {
    //------- Password encryption --------------//
    fun encryption(password: String): String {
        val algorithm = MessageDigest.getInstance("SHA1")
        val messageDigest = algorithm.digest(password.toByteArray())
        val hexString = StringBuffer()
        for (i in messageDigest.indices) {
            val hex = Integer.toHexString(0xff and messageDigest[i].toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }

    fun comparePasswordEncrypt(password: String, enteredPassword: String): Boolean {
        val md = MessageDigest.getInstance("SHA1")
        val digest = md.digest(enteredPassword.toByteArray())
        val sb = StringBuilder()
        for (b in digest) {
            sb.append(String.format("%02x", b))
        }
        return password == sb.toString()
    }

    fun isValidUsername(username: String): Boolean {
        val pattern = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$".toRegex() //user name must be between 4 and 20 characters and start with a letter
        return pattern.matches(username)
    }

}