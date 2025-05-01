package com.oscarmena.socceropenapi;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SoccerApiService {
    @GET("venues")
    Call<VenuesResponse> getVenues(@Query("api_token") String apiToken);

    @GET("venues")
    Call<VenuesResponse> searchVenues(
            @Query("api_token") String apiToken,
            @Query("name") String venueName
    );
}