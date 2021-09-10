package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseMemberDetails {
    @SerializedName("Success")
    @Expose
    var success: String? = null

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("Count")
    @Expose
    var count: Int? = null

    @SerializedName("members")
    @Expose
    var members: ArrayList<Member>? = null
}