package com.example.calatour.rest_api

import com.example.calatour.model.chat_api.AuthenticationRequest
import com.example.calatour.model.chat_api.AuthenticationResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatAPI {
    @POST("authenticate.php")
    // set the static header that specifies the encoding of the data transfer
    // this is static information and will be the same for all the calls
    @Headers( "Content-Type: application/json" )
    fun authenticate(@Body body: AuthenticationRequest): Call<AuthenticationResponse>

    @HTTP(method = "DELETE", path = "logout.php", hasBody = true)
    @Headers("Content-Type: application/json")
    fun globalLogout(@Body body: AuthenticationRequest): Call<Void>

    companion object {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun create(): ChatAPI {
            val retrofitInstance = Retrofit.Builder()
                .baseUrl("https://cgisdev.utcluj.ro/moodle/chat-piu/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build()

            return retrofitInstance.create(ChatAPI::class.java)
        }
    }
}