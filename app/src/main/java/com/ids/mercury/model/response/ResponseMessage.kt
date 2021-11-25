package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class ResponseMessage {
    @SerializedName("Success")
    @Expose
    var success: String? = ""

    @SerializedName("Message")
    @Expose
    var message: String? = null

    @SerializedName("MemberId")
    @Expose
    var memberId: Int? = 0

    @SerializedName("Id")
    @Expose
    var id: Int? = 0
}