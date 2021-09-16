package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScheduleArray {
    var day: String? = ""
    var arraySchedule: ArrayList<ClassSession>? = arrayListOf()

    constructor(day: String?, arraySchedule: ArrayList<ClassSession>?) {
        this.day = day
        this.arraySchedule = arraySchedule
    }
}