package com.ids.mercury.utils

import com.ids.mercury.model.response.ResponseMenus
import com.ids.mercury.model.response.ResponseMessage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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


}
