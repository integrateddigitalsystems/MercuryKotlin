package com.ids.mercury.utils

import com.ids.mercury.model.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import kotlin.coroutines.RestrictsSuspension

interface RetrofitInterface {


    @GET("MercuryGetLoginInfo")
    fun login(
        @Query(ApiParameters.MEMBER_ID) memberId: String,
        @Query(ApiParameters.PASSWORD) password: String
    ): Call<ResponseMessage>

    @GET("UpdateUserDevice")
    fun updateUserDevice(
        @Query(ApiParameters.CLIENT_ID) clientId: String,
        @Query(ApiParameters.DEVICE_ID) deviceId: String
    ): Call<ResponseMessage>

    @GET("AddDevice2")
    fun addDevice(
        @Query(ApiParameters.MODEL) model: String,
        @Query(ApiParameters.OS_VERSION) os_version: String,
        @Query(ApiParameters.DEVICE_TOKEN) device_token: String,
        @Query(ApiParameters.DEVICE_TYPE_ID) device_type_id: String,
        @Query(ApiParameters.IMEI) imei: String,
        @Query(ApiParameters.REGISTRATION_DATE) registration_date: String,
        @Query(ApiParameters.GETNERAL_NOTIFICATION) general_notification: Int,
        @Query(ApiParameters.APP_VERSION) app_version: String,
        @Query(ApiParameters.IS_PRODUCTION) is_production: String
    ): Call<ResponseMessage>

    @GET("GetMenus")
    fun getMenus(
        @Query(ApiParameters.KEY) key: String,
        @Query(ApiParameters.SHOW_IN_DRAWER) sid: Int,
        @Query(ApiParameters.PARENT_ID) pid: Int
    ): Call<ResponseMenus>

    @GET("MercurySendVerficationSMS")
    fun sendVerificationSms(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseMessage>


    @GET("MercuryCheckVerificationCode")
    fun checkVerification(
        @Query(ApiParameters.MEMBER_ID) memberId: String,
        @Query(ApiParameters.VERIFICATION_CODE) verificationCode: String
    ): Call<ResponseMessage>

    @GET("MercuryResetPassword")
    fun resetPassword(
        @Query(ApiParameters.MEMBER_ID) memberId: String,
        @Query(ApiParameters.PASSWORD) password: String
    ): Call<ResponseMessage>

    @GET("SaveMember")
    fun saveMember(
        @Query(ApiParameters.FIRST_NAME) firstName: String,
        @Query(ApiParameters.MIDDLE_NAME) middleName: String,
        @Query(ApiParameters.LAST_NAME) lastName: String,
        @Query(ApiParameters.PHONE) phone: String,
        @Query(ApiParameters.DOB) dob: String,
        @Query(ApiParameters.EMAIL) email: String
    ): Call<ResponseMessage>


    @GET("UpdateMember")
    fun updateMember(
        @Query(ApiParameters.MEMBER_ID) memberId: String,
        @Query(ApiParameters.DATE_OF_BIRTH) dob: String,
        @Query(ApiParameters.MOBILE) mobile: String,
        @Query(ApiParameters.EMAIL) email: String
    ): Call<ResponseMessage>

    @GET("GetMemberDetails")
    fun getMemberDetails(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseMemberDetails>

    @GET("GetMemberLoyaltyPointsHistory")
    fun getLoyalityHistory(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseHistory>


    @GET("GetMemberLoyaltyPointsTotal")
    fun getLoyalityPointsTotal(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseLoyalityPointsTotal>


    @GET("GetGymPTs")
    fun getGymPts(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseMembership>


    @GET("GetGymMember")
    fun getGymMembers(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseMembership>

    @GET("GetGymPackages")
    fun getGymPackages(
    ): Call<ResponseGymPackages>

    @GET("GetPTPackages")
    fun getPTPackages(
    ): Call<ResponsePtPackages>

    @GET("GetGymMembershipFinalAmount")
    fun getAmount(
        @Query(ApiParameters.PACKAGE_ID) packageId: Int,
        @Query(ApiParameters.REGISTRATION_DATE) regDate: String
    ): Call<ResponseAmount>


    @GET("GetSuggestedGymClasses")
    fun getSuggestedGymClasses(
    ): Call<ResponseGymClasses>

    @GET("GetGymSchedule")
    fun getGymSchedule(
        @Query(ApiParameters.FROM_DATE) fromDate: String,
        @Query(ApiParameters.TO_DATE) toDate: String
    ): Call<ResponseClassSessions>

    @GET("GetGymClassDetailsBySession")
    fun getGymClassDetailsBySession(
        @Query(ApiParameters.CLASS_SESSION_ID) class_session_id: Int
    ): Call<ResponseGymClasses>

    @GET("GetActivities")
    fun getActivities(
    ): Call<ResponseActivities>

    @GET("GetAcademyCoaches")
    fun getAcademyCoaches(
        @Query(ApiParameters.ACTIVITY_ID) class_session_id: Int
    ): Call<ResponseCoaches>

    @GET("GetAcademySChedule")
    fun getAcademySchedule(
        @Query(ApiParameters.ACTIVITY_ID) activity_id: Int,
        @Query(ApiParameters.FROM_DATE) fromDate: String,
        @Query(ApiParameters.TO_DATE) toDate: String
    ): Call<ResponseClassSessions>

    @GET("CheckAvailability")
    fun checkAvailability(
        @Query(ApiParameters.ACTIVITY_ID) activity_id: Int,
        @Query(ApiParameters.FROM) fromDate: String,
        @Query(ApiParameters.TO) toDate: String
    ): Call<ResponseCourts>


    @GET("GetMemberPayments")
    fun getMemberPayments(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponsePaymentHistory>

    @GET("GetGiftCards")
    fun getGiftCards(
    ): Call<ResponseGiftCard>

    @GET("SendRequestEmail")
    fun sendrequestEmail(
        @Query(ApiParameters.MEMBER_ID) memberId: String,
        @Query(ApiParameters.TYPE_ID) password: String
    ): Call<ResponseMessage>

    @GET("GetMemberGuestPasses")
    fun getMemberGuestPasses(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseGuestPasses>

    @GET("SendGuestPass")
    fun sendGuestPass(
        @Query(ApiParameters.MEMBER_ID) memberId: String,
        @Query(ApiParameters.NAME) name: String,
        @Query(ApiParameters.EMAIL) email: String,
        @Query(ApiParameters.MOBILE) mobile: String
    ): Call<ResponseMessage>


    @GET("GetGuestPassPackages")
    fun getGuestPassPackages(
    ): Call<ResponseGymPackages>


    @GET("GetReferrals")
    fun getReferrals(
        @Query(ApiParameters.MEMBER_ID) memberId: String
    ): Call<ResponseFriends>
}
