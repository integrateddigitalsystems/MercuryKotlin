package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InvoicePayments {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0
    @SerializedName("StatusId")
    @Expose
    var statusId: Int? = 0
    @SerializedName("Amount")
    @Expose
    var amount: String? = ""

    @SerializedName("PaymentDate")
    @Expose
    var paymentDate: String? = ""

    @SerializedName("CurrencyName")
    @Expose
    var currencyName: String? = ""

    @SerializedName("Description")
    @Expose
    var description: String? = ""


    constructor(
        id: Int?,
        statusId: Int?,
        amount: String?,
        paymentDate: String?,
        currencyName: String?,
        description: String?
    ) {
        this.id = id
        this.statusId = statusId
        this.amount = amount
        this.paymentDate = paymentDate
        this.currencyName = currencyName
        this.description = description
    }
}