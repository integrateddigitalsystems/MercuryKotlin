package com.ids.mercury.model.response



import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Friends {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("MemberId")
    @Expose
    var memberId: Int? = 0

    @SerializedName("Name")
    @Expose
    var name: String? = ""

}