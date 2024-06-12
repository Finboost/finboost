package com.wafie.finboost_frontend.utils

import android.util.Base64
import org.json.JSONObject
object Utils {

    fun jwtDecoder(token: String): JSONObject?  {
         return try {
             val parts = token.split(".")
             if (parts.size != 3) {
                 throw IllegalArgumentException("Invalid JWT token.")
             }

             val payload = parts[1]
             //decode payload from base 64
             val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)

             val decodedString = String(decodedBytes)

             JSONObject(decodedString)
         } catch (e: Exception) {
             e.printStackTrace()
             null
         }
    }
}