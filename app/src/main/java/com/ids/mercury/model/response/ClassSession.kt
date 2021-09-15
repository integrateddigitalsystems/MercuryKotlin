package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClassSession {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("ClassId")
    @Expose
    var classId: Int? = 0

    @SerializedName("Capacity")
    @Expose
    var capacity: Int? = 0

    @SerializedName("MemberCount")
    @Expose
    var memberCount: Int? = 0

    @SerializedName("AvailablePlaces")
    @Expose
    var availablePlaces: Int? = 0

    @SerializedName("Coach")
    @Expose
    var coach: String? = ""

    @SerializedName("ClassName")
    @Expose
    var className: String? = ""

    @SerializedName("Place")
    @Expose
    var place: String? = ""

    @SerializedName("Time")
    @Expose
    var time: String? = ""

    @SerializedName("EndTime")
    @Expose
    var endTime: String? = ""

    @SerializedName("FromDate")
    @Expose
    var fromDate: String? = ""

    @SerializedName("ToDate")
    @Expose
    var toDate: String? = ""

    @SerializedName("MediaFiles")
    @Expose
    var mediaFiles: ArrayList<MediaFile>? = arrayListOf()
}