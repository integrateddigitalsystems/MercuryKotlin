package com.ids.mercury.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseMembership {
    @SerializedName("Success")
    @Expose
    var success: String? = ""

    @SerializedName("Message")
    @Expose
    var message: String? = ""

    @SerializedName("Count")
    @Expose
    var count: Int? = 0

    @SerializedName("gymMembers")
    @Expose
    var gymMembers: ArrayList<MemberShip>? = arrayListOf()


}