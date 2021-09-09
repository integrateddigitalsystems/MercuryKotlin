package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Menu {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("ParentId")
    @Expose
    var parentId: Int? = 0

    @SerializedName("Label")
    @Expose
    var label: String? = ""

    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("Details")
    @Expose
    var details: Any? = null

    @SerializedName("Summary")
    @Expose
    var summary: String? = ""

    @SerializedName("Priority")
    @Expose
    var priority: Int? = 0

    @SerializedName("Published")
    @Expose
    var published: Boolean? = false

    @SerializedName("ShowInDrawer")
    @Expose
    var showInDrawer: Boolean? = false

    @SerializedName("mediaFiles")
    @Expose
    var mediaFiles: ArrayList<MediaFile>? = arrayListOf()

    @SerializedName("Children")
    @Expose
    var children: ArrayList<Child>? = arrayListOf()
}