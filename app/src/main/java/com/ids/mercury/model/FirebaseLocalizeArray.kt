package com.ids.mercury.model

import com.google.gson.annotations.SerializedName

class FirebaseLocalizeArray {
    @SerializedName("messages")
    var messages: ArrayList<FirebaseLocalizeItem>?= arrayListOf()

}