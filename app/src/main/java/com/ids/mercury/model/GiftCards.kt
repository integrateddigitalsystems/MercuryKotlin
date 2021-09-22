package com.ids.mercury.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GiftCards {


    @SerializedName("Name")
    @Expose
    var name: String? = ""


    var selected: Boolean? = false
}