package com.ids.mercury.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ids.mercury.model.GiftCards

class ResponseGiftCard {
    @SerializedName("Success")
    @Expose
    var success: String? = ""

    @SerializedName("Message")
    @Expose
    var message: String? = ""

    @SerializedName("Count")
    @Expose
    var count: Int? = 0

    @SerializedName("referrals")
    @Expose
    var referrals: ArrayList<GiftCards>? = arrayListOf()
}