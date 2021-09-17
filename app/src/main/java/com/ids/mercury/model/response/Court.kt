package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Court {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("RelatedCourt")
    @Expose
    var relatedCourt: Int? = 0

    @SerializedName("Price")
    @Expose
    var price: Double? = null

    @SerializedName("IsHalf")
    @Expose
    var isHalf: Boolean? = false

    @SerializedName("AcceptReservation")
    @Expose
    var acceptReservation: Boolean? = false
}