package com.ids.mercury.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ids.mercury.controller.MyApplication
import com.ids.mercury.utils.AppConstants


class FirebaseUrlItems {

    @SerializedName("version")
    var version: Double?=0.0
    @SerializedName("url")
    var url: String?=""



}