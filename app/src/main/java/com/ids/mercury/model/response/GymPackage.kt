package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GymPackage {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("TypeId")
    @Expose
    var typeId: Int? = 0

    @SerializedName("DaysDuration")
    @Expose
    var daysDuration: Int? = 0

    @SerializedName("Name")
    @Expose
    var name: String? = ""

    @SerializedName("Type")
    @Expose
    var type: String? = ""

    @SerializedName("Amount")
    @Expose
    var amount: Double? = 0.0

    @SerializedName("PaymentsNb")
    @Expose
    var paymentsNb: Int? = 0

    @SerializedName("DownPayment")
    @Expose
    var downPayment: Double? = 0.0

    @SerializedName("MonthlyPayment")
    @Expose
    var monthlyPayment: Double? = 0.0

    @SerializedName("EntriesNb")
    @Expose
    var entriesNb: Int? = 0

    @SerializedName("FreezesNb")
    @Expose
    var freezesNb: Int? = 0

    @SerializedName("Transferable")
    @Expose
    var transferable: Boolean? = false

    @SerializedName("AllowUserToEnter")
    @Expose
    var allowUserToEnter: Boolean? = false

    @SerializedName("CancelationFee")
    @Expose
    var cancelationFee: Double? = 0.0

    @SerializedName("FreezeFee")
    @Expose
    var freezeFee: Double? = 0.0

    @SerializedName("FreezWeeks")
    @Expose
    var freezWeeks: Int? = 0

    @SerializedName("AdditionalFees")
    @Expose
    var additionalFees: Double? = 0.0

    @SerializedName("GuestsNumber")
    @Expose
    var guestsNumber: Int? = 0

    @SerializedName("FreeMembership")
    @Expose
    var freeMembership: Boolean? = false

    @SerializedName("Swimming")
    @Expose
    var swimming: Boolean? = false

    @SerializedName("IsPartiallyPaid")
    @Expose
    var isPartiallyPaid: Boolean? = false

    @SerializedName("Rate")
    @Expose
    var rate: Double? = 0.0

    @SerializedName("CompSessions")
    @Expose
    var compSessions: Int? = 0

    @SerializedName("Duration")
    @Expose
    var duration: Int? = 0

    @SerializedName("LinkedPackageId")
    @Expose
    var linkedPackageId: Int? = 0

    @SerializedName("AutoRenewal")
    @Expose
    var autoRenewal: Boolean? = false

    @SerializedName("FreezePeriodAllowed")
    @Expose
    var freezePeriodAllowed: Double? = 0.0

    @SerializedName("UnlimitedFreeze")
    @Expose
    var unlimitedFreeze: Boolean? = false

    @SerializedName("TransferFee")
    @Expose
    var transferFee: Double? = 0.0
}