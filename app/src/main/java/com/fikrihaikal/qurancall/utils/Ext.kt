package com.fikrihaikal.qurancall.utils

import android.os.Build
import org.json.JSONObject
import java.util.Base64
import java.util.Date


fun String.decodeJwtPayload(): String {
    val parts = this.split('.')
    if (parts.size != 3) {
        throw IllegalArgumentException("Invalid JWT format")
    }

    val encodedPayload = parts[1]
    val decodedPayload = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getUrlDecoder().decode(encodedPayload)
    } else {
        android.util.Base64.decode(encodedPayload, android.util.Base64.URL_SAFE)
    }
    return String(decodedPayload, Charsets.UTF_8)
}

fun String.isJwtExpired(): Boolean {
    val payload = this.decodeJwtPayload()
    val jsonObject = JSONObject(payload)
    if (jsonObject.has("exp")) {
        val expirationTime = jsonObject.getLong("exp") * 1000
        val currentTime = Date().time
        return currentTime > expirationTime
    }
    return false
}