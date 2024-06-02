package com.wafie.finboost_frontend.data

import com.wafie.finboost_frontend.R
import com.wafie.finboost_frontend.data.model.Expert

object DummyData {
    fun getExpert(): List<Expert> {
        return listOf(
            Expert(R.drawable.wafie, "Wafie Abiyya", "Piutang"),
            Expert(R.drawable.wafie, "Abiyya El Hanief", "Keuangan"),
            Expert(R.drawable.wafie, "Hanief Assegaf", "Keuangan"),
        )
    }
}