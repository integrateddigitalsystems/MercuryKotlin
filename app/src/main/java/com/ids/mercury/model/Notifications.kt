package com.ids.mercury.model

import com.google.gson.annotations.SerializedName

class Notifications {

    var id: Int?=0

    var title: String?=""
    var text: String?=""
    var imageUrl: String?=""
    var expanded: Boolean?=false

    constructor(id: Int?, title: String?, text: String?, imageUrl: String?, expanded: Boolean?) {
        this.id = id
        this.title = title
        this.text = text
        this.imageUrl = imageUrl
        this.expanded = expanded
    }
}