package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Session {
    @SerializedName("id")
    @Expose
    var id: String? = ""

    @SerializedName("updateStatus")
    @Expose
    var updateStatus: String? = ""

    @SerializedName("version")
    @Expose
    var version: String? = ""
}