package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MediaFile {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("Caption")
    @Expose
    var caption: String? = ""

    @SerializedName("TypeId")
    @Expose
    var typeId: Int? = 0

    @SerializedName("FileName")
    @Expose
    var fileName: String? = ""

    @SerializedName("YouTubePath")
    @Expose
    var youTubePath: String? = ""

    @SerializedName("FilePath")
    @Expose
    var filePath: String? = ""

    @SerializedName("CroppedImage")
    @Expose
    var croppedImage: Any? = null

    @SerializedName("MainImage")
    @Expose
    var mainImage: Boolean? = false

    @SerializedName("IsIcon")
    @Expose
    var isIcon: Boolean? = false
}