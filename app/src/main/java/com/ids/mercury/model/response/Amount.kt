package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Amount {
    @SerializedName("Amount")
    @Expose
    var amount: Double? = 0.0

    @SerializedName("Symbol")
    @Expose
    var symbol: String? = ""

    @SerializedName("SimpleSymbol")
    @Expose
    var simpleSymbol: String? = ""
}