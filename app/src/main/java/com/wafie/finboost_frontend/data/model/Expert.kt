package com.wafie.finboost_frontend.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expert(
    var expertPhoto: Int,
    var expertName: String,
    var expertTag: String

): Parcelable