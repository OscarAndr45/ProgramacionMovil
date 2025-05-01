package com.oscarmena.socceropenapikotlin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SoccerApiService {
    @GET("venues")
    fun getVenues(@Query("api_token") apiToken: String): Call<VenuesResponse>

    @GET("venues")
    fun searchVenues(
        @Query("api_token") apiToken: String,
        @Query("name") venueName: String
    ): Call<VenuesResponse>
}
