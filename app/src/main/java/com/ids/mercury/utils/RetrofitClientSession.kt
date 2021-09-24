package com.ids.mercury.utils


import com.google.gson.GsonBuilder
import com.ids.mercury.controller.MyApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClientSession {

    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {

            val gson = GsonBuilder()
                .setLenient()
                .create()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(MyApplication.URL_CREATE_CHECKOUT_SESSION.substring(0,MyApplication.URL_CREATE_CHECKOUT_SESSION.lastIndexOf("/")+1))
                    .client(requestHeader)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }

    private/*   HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);*//* .addInterceptor(interceptor)*/ val requestHeader: OkHttpClient
        get() = OkHttpClient.Builder()
            // .addNetworkInterceptor()
            .connectTimeout(45, TimeUnit.SECONDS) // connect timeout
            .writeTimeout(45, TimeUnit.SECONDS) // write timeout
            .readTimeout(45, TimeUnit.SECONDS)
            .build()
    private fun cancelRequest() {}


}
