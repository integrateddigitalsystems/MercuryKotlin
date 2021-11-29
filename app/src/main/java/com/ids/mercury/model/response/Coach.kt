package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coach {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("Type")
    @Expose
    var type: Int? = 0

    @SerializedName("Department")
    @Expose
    var department: Int? = 0

    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("Email")
    @Expose
    var email: String? = ""

    @SerializedName("Phone")
    @Expose
    var phone: String? = ""

    @SerializedName("Mobile")
    @Expose
    var mobile: String? = ""

    @SerializedName("Address")
    @Expose
    var address: String? = ""

    @SerializedName("CoachType")
    @Expose
    var coachType: Int? = 0

    var selected : Boolean ?= false
}