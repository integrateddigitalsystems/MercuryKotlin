package com.ids.mercury.model


class SectionPagerItem (
    var id:Int,
    var title : String ?="" ,
    var lable : String ?="" ,
    var imageLocal : Int ?=0 ,
    var imageUrl : String ?="" ,
    var showImage:Boolean?=true,
    var localImage:Boolean?=true,
    var action : () -> Unit

)