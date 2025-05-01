package com.oscarmena.socceropenapikotlin

import com.google.gson.annotations.SerializedName

data class VenuesResponse(
    @SerializedName("data") val data: List<Venue>?
) {
    data class Venue(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("city_name") val cityName: String?,
        @SerializedName("address") val address: String?,
        @SerializedName("capacity") val capacity: Int?
    )
}