package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseCheckout {
    @SerializedName("merchant")
    @Expose
    var merchant: String? = ""

    @SerializedName("result")
    @Expose
    var result: String? = ""

    @SerializedName("session")
    @Expose
    var session: Session? = null

    @SerializedName("successIndicator")
    @Expose
    var successIndicator: String? = ""
}