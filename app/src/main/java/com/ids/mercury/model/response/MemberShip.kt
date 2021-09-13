package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MemberShip {

    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("PackageId")
    @Expose
    var packageId: Int? = 0

    @SerializedName("StatusId")
    @Expose
    var statusId: Int? = 0

    @SerializedName("EntriesNbLeft")
    @Expose
    var entriesNbLeft: Int? = 0


    @SerializedName("RegistrationDate")
    @Expose
    var registrationDate: String? = ""

    @SerializedName("ExpiryDate")
    @Expose
    var expiryDate: String? = ""

    @SerializedName("PackageName")
    @Expose
    var packageName: String? = ""

    @SerializedName("StatusName")
    @Expose
    var statusName: String? = ""

    @SerializedName("PackageType")
    @Expose
    var packageType: String? = ""

    constructor(
        id: Int?,
        packageId: Int?,
        statusId: Int?,
        entriesNbLeft: Int?,
        registrationDate: String?,
        expiryDate: String?,
        packageName: String?,
        statusName: String?,
        packageType: String?
    ) {
        this.id = id
        this.packageId = packageId
        this.statusId = statusId
        this.entriesNbLeft = entriesNbLeft
        this.registrationDate = registrationDate
        this.expiryDate = expiryDate
        this.packageName = packageName
        this.statusName = statusName
        this.packageType = packageType
    }
}