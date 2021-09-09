package com.ids.mercury.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FirebaseBaseUrlsArray {
    @SerializedName("android")
    @Expose
    var android: ArrayList<FirebaseUrlItems>? = null

    @SerializedName("ios")
    @Expose
    var ios: List<FirebaseUrlItems>? = null
}