package com.ids.mercury.model

import com.google.gson.annotations.SerializedName


class CountryArray {
    @SerializedName("countries")
    var countries: ArrayList<CountryCodes>? = arrayListOf()
}