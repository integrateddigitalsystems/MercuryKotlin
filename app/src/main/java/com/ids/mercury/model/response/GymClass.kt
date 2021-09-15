package com.ids.mercury.model.response


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GymClass {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("CoachId")
    @Expose
    var coachId: Int? = 0

    @SerializedName("CoachName")
    @Expose
    var coachName: String? = ""

    @SerializedName("Capacity")
    @Expose
    var capacity: Int? = 0

    @SerializedName("FromDate")
    @Expose
    var fromDate: String? = ""

    @SerializedName("ToDate")
    @Expose
    var toDate: String? = ""

    @SerializedName("TotalAttended")
    @Expose
    var totalAttended: Int? = 0

    @SerializedName("Price")
    @Expose
    var price: Double? = 0.0

    @SerializedName("PriceForMembers")
    @Expose
    var priceForMembers: Double? = 0.0

    @SerializedName("MediaFiles")
    @Expose
    var mediaFiles: ArrayList<MediaFile>? = arrayListOf()
}