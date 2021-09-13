package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PtPackage {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("DaysDuration")
    @Expose
    var daysDuration: Int? = 0

    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("ExpiryIn")
    @Expose
    var expiryIn: Double? = null

    @SerializedName("Amount")
    @Expose
    var amount: Double? = null

    @SerializedName("SessionNb")
    @Expose
    var sessionNb: Int? = 0

    @SerializedName("IsMultiUser")
    @Expose
    var isMultiUser: Boolean? = false

    @SerializedName("IsSwimming")
    @Expose
    var isSwimming: Boolean? = false

    @SerializedName("IsStepAhead")
    @Expose
    var isStepAhead: Boolean? = false

    @SerializedName("IsFree")
    @Expose
    var isFree: Boolean? = false

    @SerializedName("Installments")
    @Expose
    var installments: Boolean? = false
}