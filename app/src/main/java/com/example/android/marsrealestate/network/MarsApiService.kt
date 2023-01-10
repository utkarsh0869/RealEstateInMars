package com.example.android.marsrealestate.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://mars.udacity.com/"

/*
* Moshi object
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
/**
 * We want retrofit to fetch for a JSON response from our web service and return it as a string.
 * ScalarsConverter supports returning strings and other primitive types.
 * Added the moshi converter factory to convert the JSON response to Kotlin objects.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * Interface that defines how retrofit talks to our web server using HTTP requests.
 * Retrofit will create an object of our interface with all the methods that talk to the the server.
 */
interface MarsApiService {
        @GET("realestate")
        fun getProperties():
                Call<List<MarsProperty>>
}

/**
 * Created the MarsApi object using Retrofit to implement the MarsApiService.
 * Calling the MarsApi.retrofit object will return a retrofit object that implements MarsApiService.
 */
object MarsApi{
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}