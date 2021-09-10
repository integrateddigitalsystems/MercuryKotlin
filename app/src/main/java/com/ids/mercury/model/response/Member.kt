package com.ids.mercury.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Member {
    @SerializedName("Id")
    @Expose
    var id: Int? = 0

    @SerializedName("PID")
    @Expose
    var pid: Int? = 0

    @SerializedName("FirstName")
    @Expose
    var firstName: String? = ""

    @SerializedName("MiddleName")
    @Expose
    var middleName: String? = ""

    @SerializedName("LastName")
    @Expose
    var lastName: String? = ""

    @SerializedName("MotherName")
    @Expose
    var motherName: String? = ""

    @SerializedName("Company")
    @Expose
    var company: Any? = null

    @SerializedName("CardId")
    @Expose
    var cardId: String? = ""

    @SerializedName("Address")
    @Expose
    var address: String? = ""

    @SerializedName("Gender")
    @Expose
    var gender: String? = ""

    @SerializedName("TypeId")
    @Expose
    var typeId: Int? = 0

    @SerializedName("NationalityId")
    @Expose
    var nationalityId: Int? = 0

    @SerializedName("DateOfBirth")
    @Expose
    var dateOfBirth: String? = ""

    @SerializedName("Mobile")
    @Expose
    var mobile: String? = ""

    @SerializedName("Email")
    @Expose
    var email: String? = ""

    @SerializedName("Photo")
    @Expose
    var photo: String? = ""

    @SerializedName("BloodType")
    @Expose
    var bloodType: String? = ""

    @SerializedName("PlaceOfBirth")
    @Expose
    var placeOfBirth: String? = ""

    @SerializedName("Emplyed")
    @Expose
    var emplyed: Any? = null

    @SerializedName("EmployerName")
    @Expose
    var employerName: String? = ""

    @SerializedName("EmployerLandLine")
    @Expose
    var employerLandLine: String? = ""

    @SerializedName("EmployerEmail")
    @Expose
    var employerEmail: String? = ""

    @SerializedName("EmployerAddress")
    @Expose
    var employerAddress: String? = ""

    @SerializedName("JobTitle")
    @Expose
    var jobTitle: String? = ""

    @SerializedName("hdlWordOfMouth")
    @Expose
    var hdlWordOfMouth: String? = ""

    @SerializedName("hdlSocialMedia")
    @Expose
    var hdlSocialMedia: String? = ""

    @SerializedName("hdlFriends")
    @Expose
    var hdlFriends: String? = ""

    @SerializedName("hdlCurrentMembers")
    @Expose
    var hdlCurrentMembers: String? = ""

    @SerializedName("hdlCompany")
    @Expose
    var hdlCompany: String? = ""

    @SerializedName("hdlEvent")
    @Expose
    var hdlEvent: String? = ""

    @SerializedName("hdlLocationProximity")
    @Expose
    var hdlLocationProximity: String? = ""

    @SerializedName("hdlOther")
    @Expose
    var hdlOther: String? = ""

    @SerializedName("hdlDetails")
    @Expose
    var hdlDetails: String? = ""

    @SerializedName("hdlId")
    @Expose
    var hdlId: Int? = 0

    @SerializedName("CityofResidence")
    @Expose
    var cityofResidence: String? = ""

    @SerializedName("Street")
    @Expose
    var street: String? = ""

    @SerializedName("Facebook")
    @Expose
    var facebook: String? = ""

    @SerializedName("Instagram")
    @Expose
    var instagram: String? = ""

    @SerializedName("LandLine")
    @Expose
    var landLine: String? = ""

    @SerializedName("MedicalProblems")
    @Expose
    var medicalProblems: String? = ""

    @SerializedName("SchoolName")
    @Expose
    var schoolName: String? = ""

    @SerializedName("SchoolAddress")
    @Expose
    var schoolAddress: String? = ""

    @SerializedName("ClassCompleted")
    @Expose
    var classCompleted: String? = ""

    @SerializedName("SpecialIntrests")
    @Expose
    var specialIntrests: String? = ""

    @SerializedName("CreditCardTypeId")
    @Expose
    var creditCardTypeId: Int? = 0

    @SerializedName("CreditCardExpiryDate")
    @Expose
    var creditCardExpiryDate: Any? = null

    @SerializedName("CreditCardNumber")
    @Expose
    var creditCardNumber: Any? = null

    @SerializedName("CreditCardCurrency")
    @Expose
    var creditCardCurrency: Int? = 0

    @SerializedName("ClientTypeId")
    @Expose
    var clientTypeId: Int? = 0

    @SerializedName("Password")
    @Expose
    var password: Any? = null

    @SerializedName("VerificationCode")
    @Expose
    var verificationCode: Any? = null

    @SerializedName("CanReserve")
    @Expose
    var canReserve: Boolean? = false
    
    fun getName():String{
        return "$firstName $middleName $lastName"
    }
}