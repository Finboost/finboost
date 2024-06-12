package com.wafie.finboost_frontend.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

data class MessageModel(
    val id: String,
    val name: String,
    val message: String,
    var timestamp: Long = 0L
) {
    constructor() : this("", "", "", 0L)
}
