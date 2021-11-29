package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemNotification {
    @SerializedName("Id")
    @Expose
    var id: Int? = null

    @SerializedName("NotificationText")
    @Expose
    var notificationText: String? = ""
    @SerializedName("SentDate")
    @Expose
    var sentDate: String? = ""

    var expanded=false
}