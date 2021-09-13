package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class History {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("LoyaltyPoints")
    @Expose
    var loyaltyPoints: Int? = 0

    @SerializedName("Description")
    @Expose
    var description: String? = null

    @SerializedName("Date")
    @Expose
    var date: String? = null

    constructor(id: Int?, loyaltyPoints: Int?, description: String?, date: String?) {
        this.id = id
        this.loyaltyPoints = loyaltyPoints
        this.description = description
        this.date = date
    }
}