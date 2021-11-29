package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseNotifications {
    @SerializedName("Success")
    @Expose
    var success: String? = null

    @SerializedName("Message")
    @Expose
    var message: String? = ""

    @SerializedName("Count")
    @Expose
    var count: Int? = 0

    @SerializedName("Items")
    @Expose
    var items: ArrayList<ItemNotification>? = arrayListOf()
}