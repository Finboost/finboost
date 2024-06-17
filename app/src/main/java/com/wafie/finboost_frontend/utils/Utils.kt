package com.wafie.finboost_frontend.utils

import android.util.Base64
import org.json.JSONObject
import java.text.NumberFormat
import java.util.Locale

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

    fun generateChatRoomId(userId: String, expertId: String): String {
        return if (userId < expertId) {
            "$userId-$expertId"
        } else {
            "$expertId-$userId"
        }
    }

    fun rupiahFormatter(amount: String?): String {
        return try {
            val amountInt = amount?.toInt() ?: 0
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            numberFormat.format(amountInt)
        } catch (e: NumberFormatException) {
            "Invalid number"
        }
    }
}