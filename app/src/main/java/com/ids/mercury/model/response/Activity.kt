package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Activity {
    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("Description")
    @Expose
    var description: String? = ""

    @SerializedName("ImageURL")
    @Expose
    var imageURL: String? = ""

    @SerializedName("Id")
    @Expose
    var id: Int? = 0
}