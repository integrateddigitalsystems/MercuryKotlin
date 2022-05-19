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



    var isLocal: Boolean? = false

    var localImage: Int? = 0

    constructor(
        id: Int?,
        caption: String?,
        typeId: Int?,
        fileName: String?,
        youTubePath: String?,
        filePath: String?,
        croppedImage: Any?,
        mainImage: Boolean?,
        isIcon: Boolean?
    ) {
        this.id = id
        this.caption = caption
        this.typeId = typeId
        this.fileName = fileName
        this.youTubePath = youTubePath
        this.filePath = filePath
        this.croppedImage = croppedImage
        this.mainImage = mainImage
        this.isIcon = isIcon
    }


    constructor(
        id: Int?,
        caption: String?,
        typeId: Int?,
        fileName: String?,
        youTubePath: String?,
        filePath: String?,
        croppedImage: Any?,
        mainImage: Boolean?,
        isIcon: Boolean?,
        islocal:Boolean?,
        localImage:Int
    ) {
        this.id = id
        this.caption = caption
        this.typeId = typeId
        this.fileName = fileName
        this.youTubePath = youTubePath
        this.filePath = filePath
        this.croppedImage = croppedImage
        this.mainImage = mainImage
        this.isIcon = isIcon
        this.isLocal=islocal
        this.localImage=localImage
    }

    constructor(
        id: Int?,
        filePath: String?,
        localImage:Int,
        islocal:Boolean?
    ) {
        this.id = id
        this.filePath=filePath
        this.localImage=localImage
        this.isLocal=islocal
    }
}