package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseGuestPasses {
    @SerializedName("Success")
    @Expose
    var success: String? = ""

    @SerializedName("Message")
    @Expose
    var message: String? = ""

    @SerializedName("totalPasses")
    @Expose
    var totalPasses: Int? = 0

    @SerializedName("Count")
    @Expose
    var count: Int? = 0
}